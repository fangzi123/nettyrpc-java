package com.nettyrpc.stater;

import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.RpcClientOptions;
import com.nettyrpc.client.proxy.RpcClientProxyBeanPostProcessor;
import com.nettyrpc.interceptor.ServerCurrentLimitInterceptor;
import com.nettyrpc.server.RpcServerOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author wangff
 * @date 2019/9/20 16:09
 */
@Configuration
@ConditionalOnProperty(prefix = "nettyrpc.client", name = "enable", havingValue = "true")
@EnableConfigurationProperties(ServerFrameProperties.class)
public class NettyRpcClientAutoConfiguration {

    @Autowired
    private ServerFrameProperties properties;

    @Bean
    public RpcClientOptions rpcClientOptions() {
        RpcClientOptions rpcClientOptions=new RpcClientOptions();
        rpcClientOptions.setProtocolType(properties.getProtocolType());
        rpcClientOptions.setLoadBalanceType(properties.getLoadBalanceType());
        rpcClientOptions.setServiceId(properties.getServiceId());
        return rpcClientOptions;
    }

    @Bean(name = "rpcClient")
    public RpcClient rpcClient (RpcClientOptions rpcClientOptions) {
        return new RpcClient(properties.getRegistryAddress(),rpcClientOptions);
    }

    @Bean
    public RpcClientProxyBeanPostProcessor rpcProxyBeanPostProcessor () {
        return new RpcClientProxyBeanPostProcessor();
    }

}
