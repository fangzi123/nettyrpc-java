package com.nettyrpc.spi;


import com.nettyrpc.client.loadbalance.LoadBalanceFactory;
import com.nettyrpc.client.loadbalance.LoadBalanceManager;
import com.nettyrpc.naming.NamingServiceFactory;
import com.nettyrpc.naming.NamingServiceFactoryManager;
import com.nettyrpc.protocol.ProtocolFactory;
import com.nettyrpc.protocol.ProtocolManager;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExtensionLoaderManager {
    private static volatile ExtensionLoaderManager instance;

    public static ExtensionLoaderManager getInstance() {
        if (instance == null) {
            synchronized (ExtensionLoaderManager.class) {
                if (instance == null) {
                    instance = new ExtensionLoaderManager();
                }
            }
        }
        return instance;
    }

    private AtomicBoolean load = new AtomicBoolean(false);

    /**
     * load all extensions with java spi
     */
    public void loadAllExtensions(String encoding) {
        if (load.compareAndSet(false, true)) {
            loadNamingService();
            loadProtocol();
            loadLoadBalance();
        }
    }

    public void loadLoadBalance() {
        LoadBalanceManager loadBalanceManager = LoadBalanceManager.getInstance();
        ServiceLoader<LoadBalanceFactory> loadBalanceFactories = ServiceLoader.load(LoadBalanceFactory.class);
        for (LoadBalanceFactory loadBalanceFactory : loadBalanceFactories) {
            loadBalanceManager.registerLoadBalance(loadBalanceFactory);
        }
    }

    public void loadNamingService() {
        NamingServiceFactoryManager manager = NamingServiceFactoryManager.getInstance();
        ServiceLoader<NamingServiceFactory> namingServiceFactories = ServiceLoader.load(NamingServiceFactory.class);
        namingServiceFactories.forEach(namingServiceFactory -> {
            manager.registerNamingServiceFactory(namingServiceFactory);
        });
    }
    public void loadProtocol() {
        ProtocolManager protocolManager = ProtocolManager.getInstance();
        ServiceLoader<ProtocolFactory> protocolFactories = ServiceLoader.load(ProtocolFactory.class);
        List<ProtocolFactory> protocolFactoryList = new ArrayList<ProtocolFactory>();
        for (ProtocolFactory protocolFactory : protocolFactories) {
            protocolFactoryList.add(protocolFactory);
        }
//        Collections.sort(protocolFactoryList, new Comparator<ProtocolFactory>() {
//            @Override
//            public int compare(ProtocolFactory o1, ProtocolFactory o2) {
//                return o1.getPriority() - o2.getPriority();
//            }
//        });
        for (ProtocolFactory protocolFactory : protocolFactoryList) {
            protocolManager.registerProtocol(protocolFactory);
        }
    }
}
