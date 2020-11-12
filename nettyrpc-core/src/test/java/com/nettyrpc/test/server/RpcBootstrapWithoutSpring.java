package com.nettyrpc.test.server;

import com.nettyrpc.interceptor.Interceptor;
import com.nettyrpc.interceptor.ServerCurrentLimitInterceptor;
import com.nettyrpc.interceptor.ServerTraceInterceptor;
import com.nettyrpc.server.RpcServer;
import com.nettyrpc.server.RpcServerOptions;
import com.nettyrpc.server.currentlimit.CounterCurrentLimiter;
import com.nettyrpc.test.api.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RpcBootstrapWithoutSpring {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 18866;
        //ServiceRegistry serviceRegistry = new ServiceRegistry("127.0.0.1:2181");
        RpcServerOptions options = new RpcServerOptions();
//        options.setNamingServiceUrl("zookeeper://127.0.0.1:2181");
        options.setNamingServiceUrl("list://"+host+":"+port);
//        options.setNamingServiceUrl(PropertyUtils.get("server"));

        List<Interceptor> interceptorList = new ArrayList<>();
        interceptorList.add(new ServerTraceInterceptor());
        interceptorList.add(new ServerCurrentLimitInterceptor(new CounterCurrentLimiter(1)));
        options.setInterceptors(interceptorList);

        RpcServer rpcServer = new RpcServer(host,port,options);
        HelloService helloService = new HelloServiceImpl();
        rpcServer.addService("com.nettyrpc.test.api.HelloService", helloService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            log.error("Exception: {}", ex);
        }
    }
}
