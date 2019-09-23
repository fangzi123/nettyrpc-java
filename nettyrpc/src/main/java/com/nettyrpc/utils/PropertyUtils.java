package com.nettyrpc.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties配置文件工具类
 * 
 * @author wangff
 *
 */
public class PropertyUtils {
    private static Properties property = new Properties();
    static {
        try (
                InputStream in = PropertyUtils.class.getResourceAsStream("/custom.properties");
        ) {
            property.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return property.getProperty(key);
    }

    public static Integer getInteger(String key) {
        String value = get(key);
        return null == value ? null : Integer.valueOf(value);
    }

    public static Boolean getBoolean(String key) {
        String value = get(key);
        return null == value ? null : Boolean.valueOf(value);
    }

    public static void main(String[] args) {
        System.out.println(PropertyUtils.get("user"));
        System.out.println(PropertyUtils.getInteger("age"));
        System.out.println(PropertyUtils.getBoolean("flag"));
    }

}