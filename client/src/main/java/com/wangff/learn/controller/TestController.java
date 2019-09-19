package com.wangff.learn.controller;

import com.alibaba.fastjson.JSON;
import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.proxy.RpcClientProxy;
import com.wangff.learn.api.HelloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RpcClientProxy
    HelloService helloService;


    @RequestMapping(value = "/helloTest1")
    public String helloTest1() {
        long start = System.currentTimeMillis();
//        HelloService helloService = RpcClient.create(HelloService.class);
        String result = helloService.hello("阿萨就撒就飒飒回家撒谎就撒回家飒飒回家撒会撒娇撒");
        System.out.println("*********************"+(System.currentTimeMillis()-start));
        return JSON.toJSONString(result);
    }

}
