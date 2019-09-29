package com.nettyrpc.protocol.json;

import com.nettyrpc.protocol.Protocol;
import com.nettyrpc.protocol.ProtocolFactory;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;

/**
 * @author wangff
 * @date 2019/9/29 16:51
 */
public class JsonProtocolFactory implements ProtocolFactory {

    @Override
    public Integer getProtocolType() {
        return ProtocolTypeEnum.JSON.getType();
    }

    @Override
    public Protocol createProtocol() {
        return new JsonProtocol();
    }
}
