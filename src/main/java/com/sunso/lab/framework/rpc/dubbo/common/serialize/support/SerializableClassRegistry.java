package com.sunso.lab.framework.rpc.dubbo.common.serialize.support;

import com.esotericsoftware.kryo.Serializer;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Title:SerializableClassRegistry
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午11:10
 * @m444@126.com
 */
public abstract class SerializableClassRegistry {
    private static final Map<Class, Object> registrations = new LinkedHashMap<>();

    public static void registerClass(Class clazz) {
        registerClass(clazz, null);
    }

    public static void registerClass(Class clazz, Serializer serializer) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class registered to kryo cannot be null!");
        }
        registrations.put(clazz, serializer);
    }

    public static Map<Class, Object> getRegisteredClasses() {
        return registrations;
    }
}
