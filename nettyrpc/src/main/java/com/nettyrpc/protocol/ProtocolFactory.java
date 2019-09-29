package com.nettyrpc.protocol;

public interface ProtocolFactory {

    Integer getProtocolType();
    Protocol createProtocol();
}
