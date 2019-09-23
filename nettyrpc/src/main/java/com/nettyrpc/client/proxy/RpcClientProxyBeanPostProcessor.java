package com.nettyrpc.client.proxy;

import com.nettyrpc.client.RpcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

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
public class RpcClientProxyBeanPostProcessor implements BeanPostProcessor {

    private HashMap<String,Object> rpcProxyMap = new HashMap(256);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            RpcClientProxy rpcClientProxy = field.getAnnotation(RpcClientProxy.class);
            if (rpcClientProxy != null) {
                log.info("***********rpcClientProxy.{} init ok... {}",beanName,field.getType().getName());
                if (rpcProxyMap.get(field.getType().getName())== null) {
                    rpcProxyMap.put(field.getType().getName(),RpcClient.create(field.getType()));
                }
                try {
                    field.setAccessible(true);
                    field.set(bean,rpcProxyMap.get(field.getType().getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
