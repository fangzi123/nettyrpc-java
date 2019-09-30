package com.nettyrpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * RPC Encoder
 * @author huangyong
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;
    private int protocolType;

    public RpcEncoder(Class<?> genericClass,int protocolType) {
        this.genericClass = genericClass;
        this.protocolType = protocolType;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            //通过 protocolType 获取协议
            Protocol protocol = ProtocolManager.getInstance().getProtocol(protocolType);
            byte[] data = protocol.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
