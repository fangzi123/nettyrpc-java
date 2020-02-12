package com.nettyrpc.test.client;

import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.proxy.ObjectProxy;
import com.nettyrpc.test.api.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class ServiceTest {

    public static void main(String[] args) {
        RpcClient rpcClient= new RpcClient("list://127.0.0.1:18866");

        HelloService helloService = ObjectProxy.newProxyInstance(HelloService.class);
        String result = helloService.hello("World");
        log.info("result=============>{}",result);
        Assert.assertEquals("Hello! World", result);
    }


}
