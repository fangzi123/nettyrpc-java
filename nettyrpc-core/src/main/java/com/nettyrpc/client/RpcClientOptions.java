/*
 * Copyright (c) 2018 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nettyrpc.client;

import com.nettyrpc.client.loadbalance.LoadBalance;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by wenweihu86 on 2017/4/24.
 */
@Setter
@Getter
@NoArgsConstructor
public class RpcClientOptions {

    private int protocolType = ProtocolTypeEnum.PROTOSTUFF.getType();
    private int loadBalanceType = LoadBalance.LOAD_BALANCE_ROUND_ROBIN;
    private String serviceId = "";

    private int connectTimeoutMillis = 1000;
    private int readTimeoutMillis = 1000;
    private int writeTimeoutMillis = 1000;
    private int maxTotalConnections = 8;
    private int minIdleConnections = 8;
    private int maxTryTimes = 3;
    // Maximum time for connection idle, testWhileIdle needs to be true
    private long timeBetweenEvictionRunsMillis = 5 * 60 * 1000;
    // for fair load balance strategy only
    private int latencyWindowSizeOfFairLoadBalance = 30;
    // for fair load balance strategy only
    // the ratio of activeInstancesNum/totalInstancesNum in brpc client, if this ratio not reached,
    // fair load balance will not start, just use random load balance strategy
    private float activeInstancesRatioOfFairLoadBalance = 0.5f;
    private int healthyCheckIntervalMillis = 3000;
    // The keep alive
    private boolean keepAlive = true;
    private boolean reuseAddr = true;
    private boolean tcpNoDelay = true;
    // so linger
    private int soLinger = 5;
    // backlog
    private int backlog = 100;
    // receive buffer size
    private int receiveBufferSize = 1024 * 64;
    // send buffer size
    private int sendBufferSize = 1024 * 64;
    // keep alive time in seconds
    private int keepAliveTime = 60 * 5;
    // io threads, default use Netty default value
    private int ioThreadNum = Runtime.getRuntime().availableProcessors();
    // threads used for deserialize rpc response and execute the callback
    private int workThreadNum = Runtime.getRuntime().availableProcessors();
    /**
     * io event type, netty or jdk
     */
    // FastFutureStore's max size
    private int futureBufferSize = 1000000;
    private String encoding = "utf-8";
    private String clientName;

    // share worker thread poll and event thread pool between multi RpcClients
    private boolean globalThreadPoolSharing = false;

    public RpcClientOptions(RpcClientOptions options) {
        this.copyFrom(options);
    }

    public void copyFrom(RpcClientOptions another) {
        this.activeInstancesRatioOfFairLoadBalance = another.activeInstancesRatioOfFairLoadBalance;
        this.backlog = another.backlog;
        this.connectTimeoutMillis = another.connectTimeoutMillis;
        this.encoding = another.encoding;
        this.futureBufferSize = another.futureBufferSize;
        this.healthyCheckIntervalMillis = another.healthyCheckIntervalMillis;
        this.ioThreadNum = another.ioThreadNum;
        this.keepAlive = another.keepAlive;
        this.keepAliveTime = another.keepAliveTime;
        this.latencyWindowSizeOfFairLoadBalance = another.latencyWindowSizeOfFairLoadBalance;
        this.loadBalanceType = another.loadBalanceType;
        this.maxTotalConnections = another.maxTotalConnections;
        this.maxTryTimes = another.maxTryTimes;
        this.minIdleConnections = another.minIdleConnections;
        this.protocolType = another.protocolType;
        this.readTimeoutMillis = another.readTimeoutMillis;
        this.receiveBufferSize = another.receiveBufferSize;
        this.reuseAddr = another.reuseAddr;
        this.sendBufferSize = another.sendBufferSize;
        this.soLinger = another.soLinger;
        this.tcpNoDelay = another.tcpNoDelay;
        this.timeBetweenEvictionRunsMillis = another.timeBetweenEvictionRunsMillis;
        this.workThreadNum = another.workThreadNum;
        this.writeTimeoutMillis = another.writeTimeoutMillis;
        this.clientName = another.clientName;
        this.globalThreadPoolSharing = another.globalThreadPoolSharing;
        this.serviceId = another.serviceId;
    }

}
