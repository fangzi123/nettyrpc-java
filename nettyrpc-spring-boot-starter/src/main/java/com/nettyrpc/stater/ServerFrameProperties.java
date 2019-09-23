package com.nettyrpc.stater;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix ="nettyrpc")
public class ServerFrameProperties {

    private String registryAddress="list://127.0.0.1:18080";

    private String serverHost="127.0.0.1";
    private int serverPort=18080;

    private int tokenBucketSize=500;
    private int tokenInputRate=500;
}