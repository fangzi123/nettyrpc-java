//package com.wangff.learn.config;
//
//import com.nettyrpc.client.RpcClient;
//import com.nettyrpc.client.proxy.RpcClientProxyBeanPostProcessor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class AppClientConfig {
//
//    @Value("${registry.center.address}")
//    private String registryCenterAddress;
//
//    @Bean(name = "rpcClient")
//    public RpcClient rpcClient () {
//        return new RpcClient(registryCenterAddress);
//    }
//
//    @Bean
//    public RpcClientProxyBeanPostProcessor rpcProxyBeanPostProcessor () {
//        return new RpcClientProxyBeanPostProcessor();
//    }
//
//}