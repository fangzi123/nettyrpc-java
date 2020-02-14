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

package com.nettyrpc.naming.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.ecwid.consul.v1.health.model.HealthService;
import com.nettyrpc.client.ConnectManage;
import com.nettyrpc.client.RpcClientOptions;
import com.nettyrpc.naming.NamingService;
import com.nettyrpc.naming.RegisterInfo;
import com.nettyrpc.naming.RegistryCenterAddress;

import java.util.ArrayList;
import java.util.List;

public class ConsulNamingService implements NamingService {

    protected RegistryCenterAddress registryCenterAddress;

    public ConsulNamingService(RegistryCenterAddress registryCenterAddress) {
        this.registryCenterAddress = registryCenterAddress;
    }

    /**
     * 服务发现
     * @param rpcClientOptions
     */
    @Override
    public void subscribe(RpcClientOptions rpcClientOptions) {

        String[] hostPort = registryCenterAddress.getHostPorts().split(":");
        ConsulRawClient client = new ConsulRawClient(hostPort[0], Integer.valueOf(hostPort[1]));
        ConsulClient consul = new ConsulClient(client);
//        获取所有服务
//        Map<String, Service> map = consul.getAgentServices().getValue();
//        System.out.println(map.size()+"," +map);

        List<HealthService> healthServiceList = consul.getHealthServices(rpcClientOptions.getServiceId(), true, null).getValue();
        if (healthServiceList != null&&!healthServiceList.isEmpty()) {
            List<String> dataList = new ArrayList<>();
            healthServiceList.forEach(healthService -> {
                dataList.add(healthService.getService().getAddress()+":"+healthService.getService().getPort());
            });

            ConnectManage.getInstance().updateConnectedServer(dataList);
            System.out.println("consul healthServiceList=======>" + healthServiceList);
        }

    }

    /**
     服务注册
     * @param registerInfo
     */
    @Override
    public void register(RegisterInfo registerInfo) {
        if (registerInfo != null) {
            String[] hostPort = registryCenterAddress.getHostPorts().split(":");
            ConsulRawClient client = new ConsulRawClient(hostPort[0], Integer.valueOf(hostPort[1]));
            ConsulClient consul = new ConsulClient(client);

            NewService newService = new NewService();
            newService.setAddress(registerInfo.getHost());
            newService.setPort(registerInfo.getPort());
            newService.setName(registerInfo.getServiceId());
            NewService.Check check = new NewService.Check();
//            check.setHttp(registerInfo.getHostPort());
            check.setTcp(registerInfo.getHostPort());
            check.setInterval("5s");
            newService.setCheck(check);
            consul.agentServiceRegister(newService);
        }
    }

}
