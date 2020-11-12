package com.wangff.learn.server;


import com.alibaba.fastjson.JSONObject;
import com.nettyrpc.server.RpcService;
import com.wangff.learn.api.HelloService;
//import com.wangff.learn.mapper.UserMapper;
//import com.wangff.learn.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

//    @Autowired
//    private UserMapper userMapper;

    @Override
    public String hello(String name) {
        JSONObject json = new JSONObject();
//        List<User> a = userMapper.selectAll();
//        json.put("userlist",a);
        json.put("in",name);
        return json.toJSONString();
    }

}
