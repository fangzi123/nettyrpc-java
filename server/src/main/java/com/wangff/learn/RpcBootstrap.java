package com.wangff.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class RpcBootstrap {

    public static void main(String[] args) {
//        new ClassPathXmlApplicationContext("server-spring.xml");
//        new AnnotationConfigApplicationContext(AppConfig.class);
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(RpcBootstrap.class, args);
    }
}
