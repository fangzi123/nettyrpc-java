package com.nettyrpc.server;

import com.nettyrpc.interceptor.DefaultInterceptorChain;
import com.nettyrpc.interceptor.InterceptorChain;
import com.nettyrpc.protocol.RpcRequest;
import com.nettyrpc.protocol.RpcResponse;
import com.nettyrpc.registry.Constant;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import net.sf.cglib.reflect.FastClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * RPC Handler（RPC request processor）
 *
 * @author luxiaoxun
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(RpcHandler.class);
    /**
     * 心跳丢失次数
     */
    private int counter = 0;

    /**
     * 并发线程数控制
     */
    private static Semaphore semaphore = new Semaphore(500);

    private final Map<String, Object> handlerMap;

    public RpcHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("RemoteAddress : " + ctx.channel().remoteAddress().toString()+ " offline !");
    }
    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final RpcRequest request) throws Exception {
        //重置心跳丢失次数
        counter = 0;
        RpcServer.submit(new Runnable() {
            @Override
            public void run() {
                logger.info("Receive request: {} success,RemoteAddress: {} ,localAddress: {}" , request.getRequestId(),ctx.channel().remoteAddress().toString(),ctx.channel().localAddress().toString());
                RpcResponse response = new RpcResponse();
                response.setRequestId(request.getRequestId());
                try {
                    semaphore.acquire();
                        if (!Constant.HEART_BEAT.equals(request.getRequestId())) {
//                        Object  result = handle(request);
//                        response.setResult(result);
                            //责任链模式优化
                            InterceptorChain interceptorChain = new DefaultInterceptorChain(RpcServer.interceptors);
                            interceptorChain.intercept(request, response);
                        }else{
                            response.setResult("heartbeat ok");
                        }

                } catch (Exception t) {
                    response.setError(t.toString());
                    logger.error("RPC Server handle request error", t);
                }finally {
                    semaphore.release();
                }
                ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        logger.debug("Send response for request " + request.getRequestId());
                    }
                });
            }
        });
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className   = request.getClassName();
        Object serviceBean = handlerMap.get(className);

        Class<?>   serviceClass   = serviceBean.getClass();
        String     methodName     = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[]   parameters     = request.getParameters();

        logger.debug(serviceClass.getName());
        logger.debug(methodName);
        for (int i = 0; i < parameterTypes.length; ++i) {
            logger.debug(parameterTypes[i].getName());
        }
        for (int i = 0; i < parameters.length; ++i) {
            logger.debug(parameters[i].toString());
        }

        // JDK reflect
        /*Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);*/

        // Cglib reflect
        FastClass serviceFastClass = FastClass.create(serviceClass);
//        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
//        return serviceFastMethod.invoke(serviceBean, parameters);
        // for higher-performance
        int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
        return serviceFastClass.invoke(methodIndex, serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("server caught exception", cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("RemoteAddress : " + ctx.channel().remoteAddress().toString()+ " active !");
        super.channelActive(ctx);
    }
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)){
                // 空闲40s之后触发 (心跳包丢失)
                if (counter >= 3) {
                    // 连续丢失3个心跳包 (断开连接)
                    ctx.channel().close().sync();
                    logger.error("已与"+ctx.channel().remoteAddress()+"断开连接");
                } else {
                    counter++;
                    logger.info(ctx.channel().remoteAddress() + "丢失了第 " + counter + " 个心跳包");
                }
            }
        }
    }

}
