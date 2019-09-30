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
package com.nettyrpc.client.loadbalance;


import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.RpcClientHandler;

import java.util.List;

/**
 * load balance strategy interface
 */
public interface LoadBalance {
    // default supported load balance type
    int LOAD_BALANCE_RANDOM = 0;
    int LOAD_BALANCE_ROUND_ROBIN = 1;
    int LOAD_BALANCE_WEIGHT = 2;
    int LOAD_BALANCE_FAIR = 3;

    /**
     * select instance channel from total instances
     * @param instances total instances, often are all healthy instances
     * @return the best instance channel
     */
    RpcClientHandler selectInstance(List<RpcClientHandler> instances);

}
