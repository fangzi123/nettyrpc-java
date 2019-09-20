package com.nettyrpc.stater;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix ="nettyrpc")
public class ServerFrameProperties {

    private String registryAddress;

    private String serverHost;
    private int serverPort;

    private int tokenBucketSize;
    private int tokenInputRate;
}