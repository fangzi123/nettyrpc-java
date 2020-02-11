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
    LIST("list"),//已实现
    FILE("file"),//已实现
    ZOOKEEPER("zookeeper"),//已实现
    NACOS("nacos"),//已实现
    EUREKA("eureka"),//todo
    CONSUL("consul"),//todo
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
