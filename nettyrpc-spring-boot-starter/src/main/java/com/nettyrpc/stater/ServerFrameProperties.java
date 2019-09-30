package com.nettyrpc.stater;

import com.nettyrpc.client.loadbalance.LoadBalance;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix ="nettyrpc")
public class ServerFrameProperties {
    //common
    private String registryAddress="list://127.0.0.1:18080";
    private int protocolType= ProtocolTypeEnum.PROTOSTUFF.getType();
    //server
    private String serverHost="127.0.0.1";
    private int serverPort=18080;
    private int tokenBucketSize=500;
    private int tokenInputRate=500;
    //client
    private int loadBalanceType= LoadBalance.LOAD_BALANCE_ROUND_ROBIN;
}