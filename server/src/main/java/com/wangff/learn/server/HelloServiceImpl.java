package com.wangff.learn.server;


import com.nettyrpc.server.RpcService;
import com.wangff.learn.api.HelloService;
@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        return "Hello! " + name;
    }

}
