package com.nettyrpc.protocol.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
/**
 * 基于kryo的序列化/反序列化工具
 * @author stone
 * @date 2019/7/31 9:30
 */
public class KryoSerializer {

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        try {
            Kryo kryo = new Kryo();
            kryo.register(obj.getClass(), new BeanSerializer(kryo, obj.getClass()));
            kryo.writeObjectOrNull(output, obj, obj.getClass());
            output.flush();
            return bos.toByteArray();
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(bos);
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> cls) {
        if (bytes == null)
            return null;
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Input input = new Input(bais);
        try {
            Kryo kryo = new Kryo();
            kryo.register(cls, new BeanSerializer(kryo, cls));
            return (T) kryo.readObjectOrNull(input, cls);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(bais);
        }
    }
    
}