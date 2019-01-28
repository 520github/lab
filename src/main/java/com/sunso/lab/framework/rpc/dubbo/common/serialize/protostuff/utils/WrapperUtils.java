package com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff.utils;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff.Wrapper;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff.delegate.TimeDelegate;
import io.protostuff.runtime.DefaultIdStrategy;
import io.protostuff.runtime.RuntimeEnv;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.HashSet;
import java.util.Map;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title:WrapperUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午3:29
 * @m444@126.com
 */
public class WrapperUtils {
    private static final Set<Class<?>> WRAPPER_SET = new HashSet<>();

    static {
        if(RuntimeEnv.ID_STRATEGY instanceof DefaultIdStrategy) {
            ((DefaultIdStrategy) RuntimeEnv.ID_STRATEGY).registerDelegate(new TimeDelegate());
        }

        WRAPPER_SET.add(Map.class);
        WRAPPER_SET.add(HashMap.class);
        WRAPPER_SET.add(TreeMap.class);
        WRAPPER_SET.add(Hashtable.class);
        WRAPPER_SET.add(SortedMap.class);
        WRAPPER_SET.add(LinkedHashMap.class);
        WRAPPER_SET.add(ConcurrentHashMap.class);

        WRAPPER_SET.add(List.class);
        WRAPPER_SET.add(ArrayList.class);
        WRAPPER_SET.add(LinkedList.class);

        WRAPPER_SET.add(Vector.class);

        WRAPPER_SET.add(Set.class);
        WRAPPER_SET.add(HashSet.class);
        WRAPPER_SET.add(TreeSet.class);
        WRAPPER_SET.add(BitSet.class);

        WRAPPER_SET.add(StringBuffer.class);
        WRAPPER_SET.add(StringBuilder.class);

        WRAPPER_SET.add(BigDecimal.class);
        WRAPPER_SET.add(Date.class);
        WRAPPER_SET.add(Calendar.class);
        WRAPPER_SET.add(Time.class);

        WRAPPER_SET.add(Wrapper.class);
    }

    public static boolean needWrapper(Class<?> clazz) {
        return WRAPPER_SET.contains(clazz) || clazz.isArray() || clazz.isEnum();
    }

    public static boolean needWrapper(Object obj) {
        return needWrapper(obj.getClass());
    }
}
