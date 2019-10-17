package com.nettyrpc.naming.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * @author wangff
 * @date 2019/9/29 16:58
 */
@Getter
@AllArgsConstructor
public enum NamingTypeEnum {
    LIST("list"),
    FILE("file"),
    ZOOKEEPER("zookeeper"),
    ;

    private String type;

    public static NamingTypeEnum typeOf(String type) {
        for (NamingTypeEnum typeEnum : values()) {
            if (Objects.equals(typeEnum.type, type)) {
                return typeEnum;
            }
        }
        return null;
    }
}
