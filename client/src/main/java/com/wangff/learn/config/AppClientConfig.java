package com.wangff.learn.config;

import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.proxy.RpcClientProxyBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppClientConfig {

//    @Bean(name = "serviceDiscovery")
//    public ServiceDiscovery serviceDiscovery () {
//        return new ServiceDiscovery("127.0.0.1:2181");
//    }

    @Bean(name = "rpcClient")
    public RpcClient rpcClient () {
        return new RpcClient("list://127.0.0.1:18866");
//        return new RpcClient("zookeeper://127.0.0.1:2181");
    }

    @Bean
    public RpcClientProxyBeanPostProcessor rpcProxyBeanPostProcessor () {
        return new RpcClientProxyBeanPostProcessor();
    }

}