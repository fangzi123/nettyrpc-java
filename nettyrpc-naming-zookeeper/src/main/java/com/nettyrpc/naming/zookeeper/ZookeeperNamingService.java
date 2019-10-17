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

package com.nettyrpc.naming.zookeeper;


import com.nettyrpc.client.ConnectManage;
import com.nettyrpc.naming.NamingService;
import com.nettyrpc.naming.RegisterInfo;
import com.nettyrpc.naming.RegistryCenterAddress;
import com.nettyrpc.registry.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ZookeeperNamingService implements NamingService {
    private CountDownLatch latch = new CountDownLatch(1);

    protected RegistryCenterAddress registryCenterAddress;

    public ZookeeperNamingService(RegistryCenterAddress registryCenterAddress) {
        this.registryCenterAddress = registryCenterAddress;
    }

    @Override
    public void subscribe() {
        ZooKeeper zookeeper = connectServer();
        if (zookeeper != null) {
            watchNode(zookeeper);
        }
    }

    @Override
    public void register(RegisterInfo registerInfo) {
        if (registerInfo != null) {
            ZooKeeper zk = connectServer();
            if (zk != null) {
                AddRootNode(zk); // Add root node if not exist
                createNode(zk, registerInfo.getHostPort());
            }
        }
    }

    private ZooKeeper connectServer() {
        ZooKeeper zk = null;
        try {
            zk = new ZooKeeper(registryCenterAddress.getHostPorts(), Constant.ZK_SESSION_TIMEOUT, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                }
            });
            latch.await();
        } catch (IOException e) {
            log.error("", e);
        }
        catch (InterruptedException ex){
            log.error("", ex);
        }
        return zk;
    }

    private void AddRootNode(ZooKeeper zk){
        try {
            Stat s = zk.exists(Constant.ZK_REGISTRY_PATH, false);
            if (s == null) {
                zk.create(Constant.ZK_REGISTRY_PATH, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (KeeperException e) {
            log.error(e.toString());
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
    }

    private void createNode(ZooKeeper zk, String data) {
        try {
            byte[] bytes = data.getBytes();
            String path = zk.create(Constant.ZK_DATA_PATH, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            log.debug("create zookeeper node ({} => {})", path, data);
        } catch (KeeperException e) {
            log.error("", e);
        }
        catch (InterruptedException ex){
            log.error("", ex);
        }
    }

    private void watchNode(final ZooKeeper zk) {
        try {
            List<String> nodeList = zk.getChildren(Constant.ZK_REGISTRY_PATH, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        watchNode(zk);
                    }
                }
            });
            List<String> dataList = new ArrayList<>();
            for (String node : nodeList) {
                byte[] bytes = zk.getData(Constant.ZK_REGISTRY_PATH + "/" + node, false, null);
                dataList.add(new String(bytes));
            }
            log.info("node data: {}", dataList);
            //this.dataList = dataList;
            log.info("Service discovery triggered updating connected server node.");
            ConnectManage.getInstance().updateConnectedServer(dataList);
        } catch (KeeperException | InterruptedException e) {
            log.error("{}", e);
        }
    }

}
