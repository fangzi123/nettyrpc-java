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
import net.sf.cglib.reflect.FastClass;

@Slf4j
public class ServerInvokeInterceptor extends AbstractInterceptor{

    @Override
    public void handleRequest(RpcRequest request, RpcResponse response) throws Exception{
        log.info("ServerInvokeInterceptor-requestId:{}",request.getRequestId());

        String className   = request.getClassName();
            Object serviceBean = RpcServer.handlerMap.get(className);

            Class<?>   serviceClass   = serviceBean.getClass();
            String     methodName     = request.getMethodName();
            Class<?>[] parameterTypes = request.getParameterTypes();
            Object[]   parameters     = request.getParameters();

            log.info(serviceClass.getName());
            log.info(methodName);
            for (int i = 0; i < parameterTypes.length; ++i) {
                log.info(parameterTypes[i].getName());
            }
            for (int i = 0; i < parameters.length; ++i) {
                log.info(parameters[i].toString());
            }
            // Cglib reflect
            FastClass serviceFastClass = FastClass.create(serviceClass);
            int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
            Object  result = serviceFastClass.invoke(methodIndex, serviceBean, parameters);
            response.setResult(result);
    }

}
