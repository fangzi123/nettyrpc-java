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

package com.nettyrpc.interceptor;


import com.nettyrpc.protocol.RpcRequest;
import com.nettyrpc.protocol.RpcResponse;
import com.nettyrpc.server.currentlimit.CurrentLimiter;
import lombok.extern.slf4j.Slf4j;

/**
 * Rpc server current limit interceptor
 * Specific current limiting algorithm leaves to the {@link CurrentLimiter}
 *
 * @author wangjiayin@baidu.com
 * @since 2018/11/26
 */
@Slf4j
public class ServerCurrentLimitInterceptor extends AbstractInterceptor{

    private CurrentLimiter limiter;

    public ServerCurrentLimitInterceptor(CurrentLimiter limiter) {
        this.limiter = limiter;
    }
    @Override
    public void handleRequest(RpcRequest request, RpcResponse response) throws Exception {
        log.info("ServerCurrentLimitInterceptor-requestId:{}",request.getRequestId());
        boolean isAllowable=limiter.isAllowable(request);
        if (!isAllowable) {
            String error = "限流器【"+limiter.getClass().getName()+"】...被触发..."+" requestId:【"+request.getRequestId()+"】";
            response.setResult(error);
            throw new RuntimeException(error);
        }
    }
}
