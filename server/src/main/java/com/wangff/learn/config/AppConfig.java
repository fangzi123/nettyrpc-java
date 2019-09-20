package com.wangff.learn.config;

import com.nettyrpc.interceptor.ServerCurrentLimitInterceptor;
import com.nettyrpc.server.RpcServer;
import com.nettyrpc.server.RpcServerOptions;
import com.nettyrpc.server.currentlimit.CurrentLimiter;
import com.nettyrpc.server.currentlimit.TokenBucketCurrentLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @author wangff
 * @date 2019/9/20 11:11
 */
@Configuration
@PropertySource("classpath:rpc.properties")
public class AppConfig {

    @Value("${registry.address}")
    private String namingServiceUrl;

    @Value("${server.address.host}")
    private String host;
    @Value("${server.address.port}")
    private int port;

    @Value("${server.limit.token.bucketSize}")
    private int bucketSize;
    @Value("${server.limit.token.tokenInputRate}")
    private int tokenInputRate;

    @Bean
    public TokenBucketCurrentLimiter currentLimiter(){
        return new TokenBucketCurrentLimiter(bucketSize,tokenInputRate);
    }

    @Bean
    public ServerCurrentLimitInterceptor serverCurrentLimitInterceptor(CurrentLimiter currentLimiter){
        return new ServerCurrentLimitInterceptor(currentLimiter);
    }

    @Bean
    public RpcServerOptions rpcServerOptions(ServerCurrentLimitInterceptor serverCurrentLimitInterceptor) {
        RpcServerOptions rpcServerOptions=new RpcServerOptions();
        rpcServerOptions.setNamingServiceUrl(namingServiceUrl);
        rpcServerOptions.setInterceptors(List.of(serverCurrentLimitInterceptor));
        return rpcServerOptions;
    }

    @Bean
    public RpcServer rpcServer(RpcServerOptions rpcServerOptions) {
        return new RpcServer(host,port,rpcServerOptions);
    }
}
