package com.nettyrpc.protocol.fastjson;

import com.nettyrpc.protocol.Protocol;
import com.nettyrpc.protocol.ProtocolFactory;
import com.nettyrpc.protocol.enums.ProtocolTypeEnum;

/**
 * @author wangff
 * @date 2019/9/29 16:51
 */
public class FastJsonProtocolFactory implements ProtocolFactory {

    @Override
    public Integer getProtocolType() {
        return ProtocolTypeEnum.FASTJSON.getType();
    }

    @Override
    public Protocol createProtocol() {
        return new FastJsonProtocol();
    }
}
