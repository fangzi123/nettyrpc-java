package com.nettyrpc.protocol.json;

import com.alibaba.fastjson.JSON;
import com.nettyrpc.protocol.Protocol;

/**
 * @author wangff
 * @date 2019/9/29 16:18
 */
public class JsonProtocol implements Protocol {
    @Override
    public <T> T deserialize(byte[] data, Class<T> cls) {
//        return JsonUtil.deserialize(data,cls);
        return JSON.parseObject(data,cls);
    }

    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONBytes(obj);
//        return JsonUtil.serialize(obj);
    }
}
