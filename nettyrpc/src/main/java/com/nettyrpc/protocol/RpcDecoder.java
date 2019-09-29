package com.nettyrpc.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * RPC Decoder
 * @author huangyong
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;
    private int protocolType;

    public RpcDecoder(Class<?> genericClass,int protocolType) {
        this.genericClass = genericClass;
        this.protocolType = protocolType;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        /*if (dataLength <= 0) {
            ctx.close();
        }*/
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        //通过 protocolType 获取协议
        Protocol protocol = ProtocolManager.getInstance().getProtocol(protocolType);
        Object obj = protocol.deserialize(data,genericClass);
//        Object obj = ProtoStuffSerializationUtil.deserialize(data, genericClass);
        out.add(obj);
    }

}
