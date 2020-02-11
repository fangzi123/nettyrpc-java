package com.nettyrpc.protocol.kyro;

import com.nettyrpc.protocol.Protocol;

/**
 * @author wangff
 * @date 2019/9/29 16:21
 */
public class KryoProtocol implements Protocol {
    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
        return KryoSerializer.deserialize(data,cls);
    }

    @Override
    public <T> byte[] serialize(T obj) {
        return KryoSerializer.serialize(obj);
    }
}
