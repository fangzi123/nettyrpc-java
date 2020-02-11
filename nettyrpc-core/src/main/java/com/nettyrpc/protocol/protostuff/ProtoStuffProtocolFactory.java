package com.nettyrpc.protocol.protostuff;

import com.nettyrpc.protocol.Protocol;
import com.nettyrpc.protocol.ProtocolFactory;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;

/**
 * @author wangff
 * @date 2019/9/29 17:17
 */
public class ProtoStuffProtocolFactory implements ProtocolFactory {
    @Override
    public Integer getProtocolType() {
        return ProtocolTypeEnum.PROTOSTUFF.getType();
    }

    @Override
    public Protocol createProtocol() {
        return new ProtostuffProtocol();
    }
}
