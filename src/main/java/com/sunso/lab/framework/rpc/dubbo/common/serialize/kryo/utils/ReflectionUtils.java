package com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo.utils;

/**
 * @Title:ReflectionUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午2:06
 * @m444@126.com
 */
public abstract class ReflectionUtils {

    public static boolean checkZeroArgConstructor(Class clazz) {
        try {
            clazz.getDeclaredConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    public static boolean isJdk(Class clazz) {
        return clazz.getName().startsWith("java.") || clazz.getName().startsWith("javax.");
    }
}
