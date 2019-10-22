package com.nettyrpc.stater;

import com.nettyrpc.client.loadbalance.LoadBalance;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix ="nettyrpc")
public class ServerFrameProperties {
    //common
    private String serviceId="example";
    private String registryAddress="list://127.0.0.1:18080";
    private int protocolType= ProtocolTypeEnum.PROTOSTUFF.getType();

    //server
    private String serverHost="127.0.0.1";
    private int serverPort=18080;
        //默认令牌桶算法
    private int tokenBucketSize=500;
    private int tokenInputRate=500;
        //计数器算法 当maxQps!=0时，为计数器算法
    private int maxQps=0;

    //client
    private int loadBalanceType= LoadBalance.LOAD_BALANCE_ROUND_ROBIN;
}