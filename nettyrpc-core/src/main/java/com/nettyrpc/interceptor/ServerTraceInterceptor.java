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

package com.nettyrpc.interceptor;

import com.nettyrpc.protocol.RpcRequest;
import com.nettyrpc.protocol.RpcResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor for server side tracing.
 * <p>
 * This interceptor is just a placeholder for integration with tracing tools using AOP.
 */
@Slf4j
public class ServerTraceInterceptor extends  AbstractInterceptor {

    @Override
    public void handleRequest(RpcRequest request, RpcResponse response) throws Exception {
        log.info("ServerTraceInterceptor-requestId:{}",request.getRequestId());
    }

}
