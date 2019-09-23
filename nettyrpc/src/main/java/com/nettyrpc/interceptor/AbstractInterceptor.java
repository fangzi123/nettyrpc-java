package com.nettyrpc.interceptor;

import com.nettyrpc.protocol.RpcRequest;
import com.nettyrpc.protocol.RpcResponse;

public abstract class AbstractInterceptor implements Interceptor {

    @Override
    public void handleNext(RpcRequest request, RpcResponse response, InterceptorChain chain) throws Exception{
        chain.intercept(request,response);
    }

}
