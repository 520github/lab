package com.sunso.lab.framework.proxy.temp;

import com.sunso.lab.framework.proxy.ReflectiveMethodInvocation;
import com.sunso.lab.framework.proxy.SunsoAopProxy;
import com.sunso.lab.framework.proxy.SunsoInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title:TempJdkDynamicAopProxy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午5:13
 * @miaoxuehui@panda-fintech.com
 */
public class TempJdkDynamicAopProxy implements SunsoAopProxy, InvocationHandler {

    private Object targetObject;
    private SunsoInterceptor interceptor;

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(interceptor == null) {
            //调用目标方法
            return method.invoke(targetObject, args);
        }
        return interceptor.intercept(new ReflectiveMethodInvocation(targetObject, method, args));
    }

    public Object getProxy(Object targetObject, SunsoInterceptor interceptor) {
        this.targetObject = targetObject;
        this.interceptor = interceptor;
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
    }

    public Object getProxy(Object targetObject) {
        return getProxy(targetObject, null);
    }

    public Object getProxy() {
        return getProxy(null, null);
    }

    public Object getProxy(ClassLoader classLoader) {
        return null;
    }
}
