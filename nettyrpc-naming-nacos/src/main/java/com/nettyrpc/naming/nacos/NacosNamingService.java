/*
 * Copyright (c) 2018 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this list except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nettyrpc.naming.nacos;


import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.nettyrpc.client.ConnectManage;
import com.nettyrpc.client.RpcClientOptions;
import com.nettyrpc.naming.NamingService;
import com.nettyrpc.naming.RegisterInfo;
import com.nettyrpc.naming.RegistryCenterAddress;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NacosNamingService implements NamingService {

    protected RegistryCenterAddress registryCenterAddress;

    public NacosNamingService(RegistryCenterAddress registryCenterAddress) {
        this.registryCenterAddress = registryCenterAddress;
    }

    /**
     * 服务发现
     * curl -X GET
     * 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'
     * @param rpcClientOptions
     */
    @Override
    public void subscribe(RpcClientOptions rpcClientOptions) {
        Properties properties = new Properties();
        properties.setProperty("serverAddr",registryCenterAddress.getHostPorts());
        try {
            com.alibaba.nacos.api.naming.NamingService naming = NamingFactory.createNamingService(properties);
                naming.subscribe(rpcClientOptions.getServiceId(), new EventListener() {
                    @Override
                    public void onEvent(Event event) {
                        List<String> dataList = new ArrayList<>();
                        List<Instance> instanceList = ((NamingEvent) event).getInstances();
                        instanceList.forEach(instance -> {
                            dataList.add(instance.getIp()+":"+instance.getPort());
                        });
                        ConnectManage.getInstance().updateConnectedServer(dataList);
                    }
                });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     服务注册
     curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance
     ?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
     * @param registerInfo service/group/version info
     */
    @Override
    public void register(RegisterInfo registerInfo) {
        if (registerInfo != null) {
            Properties properties = new Properties();
            properties.setProperty("serverAddr",registryCenterAddress.getHostPorts());
            try {
                com.alibaba.nacos.api.naming.NamingService naming = NamingFactory.createNamingService(properties);
                naming.registerInstance(registerInfo.getServiceId(), registerInfo.getHost(), registerInfo.getPort(), "DEFAULT");
            } catch (NacosException e) {
                e.printStackTrace();
            }
        }
    }

}
