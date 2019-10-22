package com.nettyrpc.protocol.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author wangff
 * @date 2019/9/29 16:58
 */
@Getter
@AllArgsConstructor
public enum ProtocolTypeEnum {
    PROTOSTUFF(1),
    FASTJSON(2),
    KRYO(3),
    ;

    private int type;

    public static ProtocolTypeEnum typeOf(Integer type) {
        for (ProtocolTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
