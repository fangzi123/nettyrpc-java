package com.wangff.learn;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RpcBootstrap {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("server-spring.xml");
//         new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
