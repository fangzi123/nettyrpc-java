/*
 * Copyright (c) 2018 Baidu, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

package com.nettyrpc.protocol;

import com.nettyrpc.protocol.enums.ProtocolTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huwenwei on 2017/9/23.
 */
@Slf4j
public class ProtocolManager {
    private Map<Integer, ProtocolFactory> protocolFactoryMap = new HashMap<Integer, ProtocolFactory>();
    private Map<Integer, Protocol> protocolMap = new HashMap<Integer, Protocol>();

    private static ProtocolManager instance;

    public static ProtocolManager getInstance() {
        if (instance == null) {
            synchronized (ProtocolManager.class) {
                if (instance == null) {
                    instance = new ProtocolManager();
                }
            }
        }
        return instance;
    }

    private ProtocolManager() {
    }

    /**
     * application can register custom protocol
     */
    public void registerProtocol(ProtocolFactory protocolFactory) {
        Integer protocolType = protocolFactory.getProtocolType();
        if (protocolFactoryMap.get(protocolType) != null) {
            throw new RuntimeException("protocol exist, type=" + protocolType);
        }
        Protocol protocol = protocolFactory.createProtocol();
        protocolMap.put(protocolType, protocol);
        protocolFactoryMap.put(protocolType, protocolFactory);
        log.info("register protocol:{}-{} success", ProtocolTypeEnum.typeOf(protocolType).name(),protocolType);
    }

    public Protocol getProtocol(Integer protocolType) {
        Protocol protocol = protocolMap.get(protocolType);
        if (protocol != null) {
            log.info("use protocol:{}", ProtocolTypeEnum.typeOf(protocolType).name());
            return protocol;
        }

        throw new RuntimeException("protocol not exist, type=" + protocolType);
    }

    public Map<Integer, Protocol> getProtocolMap() {
        return protocolMap;
    }

}
