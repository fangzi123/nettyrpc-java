/*
 * Copyright (c) 2019 Baidu, Inc. All Rights Reserved.
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
package com.nettyrpc.client.loadbalance.roundrobin;


import com.nettyrpc.client.RpcClientHandler;
import com.nettyrpc.client.loadbalance.LoadBalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Round-robin load balance strategy implementation
 */
class RoundRobinLoadBalance implements LoadBalance {

    private AtomicLong counter = new AtomicLong(0);


    @Override
    public RpcClientHandler selectInstance(List<RpcClientHandler> instances) {
        long instanceNum = instances.size();
        if (instanceNum == 0) {
            return null;
        }
        int index = (int) (counter.getAndIncrement() % instanceNum);
        RpcClientHandler handler = instances.get(index);
        return handler;
    }
}
