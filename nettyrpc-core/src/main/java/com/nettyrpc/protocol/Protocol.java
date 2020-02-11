package com.nettyrpc.protocol;

/**
 * @author wangff
 * @date 2019/9/29 16:16
 */
public interface Protocol {
    public  <T> T deserialize(byte[] data, Class<T> cls) ;

    public <T> byte[] serialize(T obj);
}
