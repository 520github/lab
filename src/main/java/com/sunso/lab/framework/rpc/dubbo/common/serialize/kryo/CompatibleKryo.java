package com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo.utils.ReflectionUtils;

/**
 * @Title:CompatibleKryo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午2:04
 * @m444@126.com
 */
public class CompatibleKryo extends Kryo {

    public Serializer getDefaultSerializer(Class type) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        }

        if (!ReflectionUtils.isJdk(type)
                && !type.isArray() && !type.isEnum()
                && !ReflectionUtils.checkZeroArgConstructor(type)) {
            return new JavaSerializer();
        }

        return super.getDefaultSerializer(type);
    }
}
