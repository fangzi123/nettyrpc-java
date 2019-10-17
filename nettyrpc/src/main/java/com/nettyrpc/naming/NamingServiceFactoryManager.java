package com.nettyrpc.naming;

import com.nettyrpc.naming.enums.NamingTypeEnum;
import com.nettyrpc.naming.file.FileNamingServiceFactory;
import com.nettyrpc.naming.list.ListNamingServiceFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class NamingServiceFactoryManager {
    private static volatile NamingServiceFactoryManager instance;

    private Map<String, NamingServiceFactory> namingServiceFactoryMap;

    public static NamingServiceFactoryManager getInstance() {
        if (instance == null) {
            synchronized (NamingServiceFactoryManager.class) {
                if (instance == null) {
                    instance = new NamingServiceFactoryManager();
                }
            }
        }
        return instance;
    }

    private NamingServiceFactoryManager() {
        this.namingServiceFactoryMap = new HashMap<String, NamingServiceFactory>();
//        this.namingServiceFactoryMap.put(NamingTypeEnum.LIST.getType(), new ListNamingServiceFactory());
//        this.namingServiceFactoryMap.put(NamingTypeEnum.FILE.getType(), new FileNamingServiceFactory());
    }

    public void registerNamingServiceFactory(NamingServiceFactory namingServiceFactory) {
        if (namingServiceFactoryMap.get(namingServiceFactory.getName()) != null) {
            throw new RuntimeException("naming service exist:" + namingServiceFactory.getName());
        }
        namingServiceFactoryMap.put(namingServiceFactory.getName(), namingServiceFactory);
        log.info("register naming service:{} success", namingServiceFactory.getName());
    }

    public NamingServiceFactory getNamingServiceFactory(String name) {
        return namingServiceFactoryMap.get(name);
    }

}
