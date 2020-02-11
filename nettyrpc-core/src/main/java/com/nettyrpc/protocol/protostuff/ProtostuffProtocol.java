package com.nettyrpc.protocol.protostuff;

import com.nettyrpc.protocol.Protocol;

/**
 * @author wangff
 * @date 2019/9/29 16:21
 */
public class ProtostuffProtocol implements Protocol {
    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        return ProtoStuffSerializationUtil.deserialize(data,cls);
    }

    @Override
    public <T> byte[] serialize(T obj) {
        return ProtoStuffSerializationUtil.serialize(obj);
    }
}
