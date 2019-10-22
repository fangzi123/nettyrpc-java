package com.nettyrpc.stater;

import com.nettyrpc.interceptor.ServerCurrentLimitInterceptor;
import com.nettyrpc.server.RpcServer;
import com.nettyrpc.server.RpcServerOptions;
import com.nettyrpc.server.currentlimit.CounterCurrentLimiter;
import com.nettyrpc.server.currentlimit.CurrentLimiter;
import com.nettyrpc.server.currentlimit.TokenBucketCurrentLimiter;
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
@ConditionalOnProperty(prefix = "nettyrpc.server", name = "enable", havingValue = "true")
@EnableConfigurationProperties(ServerFrameProperties.class)
public class NettyRpcServerAutoConfiguration {

    @Autowired
    private ServerFrameProperties properties;

    @Bean
    public CurrentLimiter currentLimiter(){
        if (properties.getMaxQps()!=0) {
            return new CounterCurrentLimiter(properties.getMaxQps());
        }else{
            return new TokenBucketCurrentLimiter(properties.getTokenBucketSize(),properties.getTokenInputRate());
        }
    }

    @Bean
    public ServerCurrentLimitInterceptor serverCurrentLimitInterceptor(CurrentLimiter currentLimiter){
        return new ServerCurrentLimitInterceptor(currentLimiter);
    }

    @Bean
    public RpcServerOptions rpcServerOptions(ServerCurrentLimitInterceptor serverCurrentLimitInterceptor) {
        RpcServerOptions rpcServerOptions=new RpcServerOptions();
        rpcServerOptions.setNamingServiceUrl(properties.getRegistryAddress());
        rpcServerOptions.setProtocolType(properties.getProtocolType());
        rpcServerOptions.setServiceId(properties.getServiceId());

        rpcServerOptions.setInterceptors(List.of(serverCurrentLimitInterceptor));
        return rpcServerOptions;
    }

    @Bean
    public RpcServer rpcServer(RpcServerOptions rpcServerOptions) {
        return new RpcServer(properties.getServerHost(),properties.getServerPort(),rpcServerOptions);
    }
}
