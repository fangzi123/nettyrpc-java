package com.nettyrpc.client.proxy;

import com.nettyrpc.client.ConnectManage;
import com.nettyrpc.client.RPCFuture;
import com.nettyrpc.client.RpcClientHandler;
import com.nettyrpc.client.loadbalance.LoadBalance;
import com.nettyrpc.client.loadbalance.LoadBalanceManager;
import com.nettyrpc.protocol.RpcRequest;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
public class Invocation {
    /**
     * 目标对象
     */
    private Object target;
    /**
     * 执行的方法
     */
    private Method method;
    /**
     * 方法的参数
     */
    private Object[] args;
    
    //省略getset
    public Invocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }

    /**
     * 执行目标对象的方法
     */
    public Object process() throws Exception{
        //method.invoke(target,args);

        RpcRequest request = new RpcRequest();
        request.setRequestId(UUID.randomUUID().toString());
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameters(args);
        // Debug
        log.debug(method.getDeclaringClass().getName());
        log.debug(method.getName());
        for (int i = 0; i < method.getParameterTypes().length; ++i) {
            log.debug(method.getParameterTypes()[i].getName());
        }
        for (int i = 0; i < args.length; ++i) {
            log.debug(args[i].toString());
        }

        LoadBalance loadBalance = LoadBalanceManager.getInstance().getLoadBalance(ConnectManage.getInstance().getRpcClientOptions().getLoadBalanceType());
        RpcClientHandler handler =loadBalance.selectInstance(ConnectManage.getInstance().getConnectedHandlers());
        RPCFuture rpcFuture = handler.sendRequest(request);
        return rpcFuture.get();
    }
}
