package com.nettyrpc.server;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author wangff
 * @date 2020/2/13 9:59
 */
@Setter
@Getter
@Slf4j
public class RpcServiceExporter implements ApplicationContextAware, InitializingBean,DisposableBean {

    private RpcServer rpcServer;

    @Override
    public void destroy() throws Exception {
        if (rpcServer != null) {
            rpcServer.stop();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        rpcServer.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (MapUtils.isNotEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
                log.info("Spring Loading service=========>{}", interfaceName);
                rpcServer.handlerMap.put(interfaceName, serviceBean);
            }
        }
    }
}
