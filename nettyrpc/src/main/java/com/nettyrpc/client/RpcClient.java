package com.nettyrpc.client;

import com.nettyrpc.client.proxy.IAsyncObjectProxy;
import com.nettyrpc.client.proxy.ObjectProxy;
import com.nettyrpc.naming.NamingService;
import com.nettyrpc.naming.NamingServiceFactory;
import com.nettyrpc.naming.NamingServiceFactoryManager;
import com.nettyrpc.naming.RegistryCenterAddress;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;
import com.nettyrpc.spi.ExtensionLoaderManager;
import lombok.Getter;

import java.lang.reflect.Proxy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RPC Client（Create RPC proxy）
 *
 * @author luxiaoxun
 */
@Getter
public class RpcClient {
    private NamingService namingService;
    private String registryCenterAddress;

    private RpcClientOptions rpcClientOptions;

    private static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(16, 16,
            600L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(65536));

    public RpcClient(String registryCenterAddress) {
        this(registryCenterAddress, new RpcClientOptions());
    }

    public RpcClient(String registryCenterAddress,RpcClientOptions rpcClientOptions) {
        this.registryCenterAddress = registryCenterAddress;
        this.rpcClientOptions = rpcClientOptions;
        ExtensionLoaderManager.getInstance().loadAllExtensions("utf-8");
        RegistryCenterAddress url = new RegistryCenterAddress(registryCenterAddress);
        NamingServiceFactory namingServiceFactory = NamingServiceFactoryManager.getInstance().getNamingServiceFactory(url.getSchema());
        namingService = namingServiceFactory.createNamingService(url);
        namingService.subscribe(rpcClientOptions);
        ConnectManage.getInstance().setRpcClientOptions(this.rpcClientOptions);
    }

    public static <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new ObjectProxy<T>(interfaceClass);
    }

    public static void submit(Runnable task) {
        threadPoolExecutor.submit(task);
    }

    public void stop() {
        threadPoolExecutor.shutdown();
        //serviceDiscovery.stop();
        ConnectManage.getInstance().stop();
    }
}

