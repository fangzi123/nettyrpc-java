package com.nettyrpc.client.proxy;

import com.nettyrpc.client.RpcClient;
import com.nettyrpc.client.RpcClientOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 后置处理器扩展
 * @author wangff
 * @date 2019/8/26 17:17
 */
@Slf4j
@Component
public class RpcClientProxyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private HashMap<String,Object> rpcProxyMap = new HashMap(256);


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
//        RpcClientOptions rpcClientOptions = new RpcClientOptions();
//        rpcClientOptions.setEncoding("gbk");
//        beanFactory.registerSingleton("rpcClientOptions",rpcClientOptions);


        String[] beanDefinitionNames=beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition=beanFactory.getBeanDefinition(beanDefinitionName);
            StringValueResolver valueResolver=new StringValueResolver() {
                @Override
                public String resolveStringValue(String s) {
                    if (s.equals("gbk")) {
                        return "gbk*********";
                    }
                    if (s.equals("utf-8")) {
                        return "utf*********";
                    }
                    return s;
                }
            };
            BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);
            visitor.visitBeanDefinition(beanDefinition);
        }

//        RpcClientOptions a=beanFactory.getBean("rpcClientOptions",RpcClientOptions.class);

    }
}
