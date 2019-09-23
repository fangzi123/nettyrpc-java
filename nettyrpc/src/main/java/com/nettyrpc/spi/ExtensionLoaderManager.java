package com.nettyrpc.spi;



import com.nettyrpc.naming.NamingServiceFactory;
import com.nettyrpc.naming.NamingServiceFactoryManager;

import java.util.*;
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
        }
    }

    public void loadNamingService() {
        NamingServiceFactoryManager manager = NamingServiceFactoryManager.getInstance();
        ServiceLoader<NamingServiceFactory> namingServiceFactories = ServiceLoader.load(NamingServiceFactory.class);
        for (NamingServiceFactory namingServiceFactory : namingServiceFactories) {
            manager.registerNamingServiceFactory(namingServiceFactory);
        }
    }

}
