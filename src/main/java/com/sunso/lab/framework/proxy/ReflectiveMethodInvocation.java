package com.sunso.lab.framework.proxy;

import com.sunso.lab.framework.proxy.common.SunsoAopUtils;

import java.lang.reflect.Method;

/**
 * @Title:ReflectiveMethodInvocation
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午5:47
 * @miaoxuehui@panda-fintech.com
 */
public class ReflectiveMethodInvocation implements ProxyMethodInvocation {
    private Method method;
    private Object[] args;
    private Object target;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] args) {
        this.target = target;
        this.method = method;
        this.args = args;
    }


    public Object proceed() throws Throwable {
        return SunsoAopUtils.invokeReflection(target, method, args);
    }
}
