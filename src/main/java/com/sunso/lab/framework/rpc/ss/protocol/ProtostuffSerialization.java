package com.sunso.lab.framework.rpc.ss.protocol;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title:ProtostuffSerialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午6:11
 * @m444@126.com
 */
public class ProtostuffSerialization {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<Class<?>, Schema<?>>();

    private static Objenesis objenesis = new ObjenesisStd(true);

    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);
        if(schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            if(schema != null) {
                cachedSchema.put(cls, schema);
            }
        }
        return schema;
    }

    @SuppressWarnings("unchecked")
    public static <T> byte[] serialize(T obj) {
        Class<T>  cls = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        Schema<T> schema = getSchema(cls);
        try {
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }finally {
            buffer.clear();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] data, Class<T> cls) {
        try{
            T t = objenesis.newInstance(cls);
            Schema<T> schema = getSchema(cls);
            ProtostuffIOUtil.mergeFrom(data, t, schema);
            return t;
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

}
