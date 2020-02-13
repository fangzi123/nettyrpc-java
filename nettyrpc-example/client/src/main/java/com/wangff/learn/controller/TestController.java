package com.wangff.learn.controller;

import com.nettyrpc.client.proxy.ObjectProxy;
import com.nettyrpc.client.proxy.RpcClientProxy;
import com.wangff.learn.api.HelloService;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RpcClientProxy
    HelloService helloService;


    @RequestMapping(value = "/helloTest1")
    public String helloTest1() {
        StopWatch stopWatch = new StopWatch();
//        HelloService helloService = ObjectProxy.newProxyInstance(HelloService.class);
        String result = helloService.hello("=========测试===============");
        System.out.println("*********************"+stopWatch.getTotalTimeMillis());
        return result;
    }

}
