package com.sunso.lab.framework.proxy.common;

import java.lang.reflect.Method;

/**
 * @Title:SunsoAopUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午5:52
 * @miaoxuehui@panda-fintech.com
 */
public class SunsoAopUtils {

    public static Object invokeReflection(Object target, Method method, Object[] args) throws Throwable {
        try {
            SunsoReflectionUtils.makeAccessible(method);
            return method.invoke(target, args);
        }catch (Exception ex) {
            throw ex;
        }
    }
}
