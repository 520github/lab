package com.sunso.lab.framework.proxy.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Title:SunsoReflectionUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午5:56
 * @miaoxuehui@panda-fintech.com
 */
public abstract class SunsoReflectionUtils {
    public static void makeAccessible(Method method) {
        if ((!Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers())) && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }
}
