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
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
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
public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
    private String host;
    private int port;
    //private String serverAddress;
    //    private ServiceRegistry serviceRegistry;
    private RegisterInfo registerInfo = null;
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
        RegisterInfo rhs = new RegisterInfo();
        rhs.setHost(host);
        rhs.setPort(port);
        rhs.setServiceId(options.getServiceId());
        registerInfo = new RegisterInfo(rhs);
        this.rpcServerOptions = options;
        if (options.getInterceptors() != null) {
            interceptors.addAll(options.getInterceptors());
        }
        interceptors.add(new ServerInvokeInterceptor());
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
                                    .addLast(new RpcDecoder(RpcRequest.class,rpcServerOptions.getProtocolType()))
                                    .addLast(new RpcEncoder(RpcResponse.class,rpcServerOptions.getProtocolType()))
                                    .addLast(new RpcHandler(handlerMap));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(host, port).sync();
            logger.info("Server started on port {}", port);

            ExtensionLoaderManager.getInstance().loadAllExtensions("utf-8");
            if (!StringUtil.isNullOrEmpty(rpcServerOptions.getNamingServiceUrl())) {
                RegistryCenterAddress registryCenterAddress = new RegistryCenterAddress(rpcServerOptions.getNamingServiceUrl());
                NamingServiceFactory namingServiceFactory = NamingServiceFactoryManager.getInstance().getNamingServiceFactory(registryCenterAddress.getSchema());
                namingService = namingServiceFactory.createNamingService(registryCenterAddress);
                namingService.register(registerInfo);
            }
            future.channel().closeFuture().sync();
        }
    }

}
