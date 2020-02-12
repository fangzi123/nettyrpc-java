package com.nettyrpc.test.client;

import com.nettyrpc.client.proxy.ObjectProxy;
import com.nettyrpc.test.api.HelloService;
import com.nettyrpc.test.server.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

@Slf4j
public class ServiceTest {

    public static void main(String[] args) {
        HelloService helloService = ObjectProxy.newProxyInstance(HelloService.class);
        String result = helloService.hello("World");
        log.info("result=============>{}",result);
        Assert.assertEquals("Hello! World", result);
    }


}
