package com.nettyrpc.server;

import com.nettyrpc.interceptor.Interceptor;
import com.nettyrpc.interceptor.ServerInvokeInterceptor;
import com.nettyrpc.naming.*;
import com.nettyrpc.protocol.RpcDecoder;
import com.nettyrpc.protocol.RpcEncoder;
import com.nettyrpc.protocol.RpcRequest;
import com.nettyrpc.protocol.RpcResponse;
import com.nettyrpc.spi.ExtensionLoaderManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RPC Server
 *
 * @author huangyong, luxiaoxun
 */
@Getter
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private String host;
    private int port;
    //private String serverAddress;
    //    private ServiceRegistry serviceRegistry;
    private RegisterInfo registerInfo = new RegisterInfo();
    private NamingService namingService;
    private RpcServerOptions rpcServerOptions = new RpcServerOptions();

    public static List<Interceptor> interceptors = new ArrayList<Interceptor>();
    public static Map<String, Object> handlerMap = new HashMap<>();

    private static ThreadPoolExecutor threadPoolExecutor;

    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;

//    public RpcServer(String host) {
//        //this.serverAddress = serverAddress;
//    }

    public RpcServer(String host,int port,final RpcServerOptions options) {
        this.host = host;
        this.port = port;
        registerInfo.setHostPort(host+":"+port);
        this.rpcServerOptions = options;
        if (options.getInterceptors() != null) {
            interceptors.addAll(options.getInterceptors());
        }
        interceptors.add(new ServerInvokeInterceptor());
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> serviceBeanMap = ctx.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                logger.info("Loading service: {}", interfaceName);
                handlerMap.put(interfaceName, serviceBean);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

    public void stop() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }

    public static void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (RpcServer.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = new ThreadPoolExecutor(16, 16, 600L,
                            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));
                }
            }
        }
        threadPoolExecutor.submit(task);
    }

    public RpcServer addService(String interfaceName, Object serviceBean) {
        if (!handlerMap.containsKey(interfaceName)) {
            logger.info("Loading service: {}", interfaceName);
            handlerMap.put(interfaceName, serviceBean);
        }

        return this;
    }

    public void start() throws Exception {


        if (bossGroup == null && workerGroup == null) {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new IdleStateHandler(15,0,0,TimeUnit.SECONDS))
                                    .addLast(new LengthFieldBasedFrameDecoder(65536, 0, 4, 0, 0))
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new RpcHandler(handlerMap));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            //String[] array = serverAddress.split(":");
            //String host = array[0];
            //int port = Integer.parseInt(array[1]);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            logger.info("Server started on port {}", port);


            ExtensionLoaderManager.getInstance().loadAllExtensions("utf-8");
            if (!StringUtils.isEmpty(rpcServerOptions.getNamingServiceUrl())) {
                RegistryCenterAddress registryCenterAddress = new RegistryCenterAddress(rpcServerOptions.getNamingServiceUrl());
                NamingServiceFactory namingServiceFactory = NamingServiceFactoryManager.getInstance()
                        .getNamingServiceFactory(registryCenterAddress.getSchema());
                namingService = namingServiceFactory.createNamingService(registryCenterAddress);

                namingService.register(registerInfo);
            }
//            if (serviceRegistry != null) {
//                serviceRegistry.register(serverAddress);
//            }

            future.channel().closeFuture().sync();
        }
    }

}
