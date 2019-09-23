package com.nettyrpc.stater;

import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.proxy.RpcClientProxyBeanPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean(name = "rpcClient")
    public RpcClient rpcClient () {
        return new RpcClient(properties.getRegistryAddress());
    }

    @Bean
    public RpcClientProxyBeanPostProcessor rpcProxyBeanPostProcessor () {
        return new RpcClientProxyBeanPostProcessor();
    }

}
