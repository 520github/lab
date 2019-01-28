package com.sunso.lab.framework.rpc.dubbo.rpc.proxy.jdk;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.proxy.AbstractProxyFactory;
import com.sunso.lab.framework.rpc.dubbo.rpc.proxy.AbstractProxyInvoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.proxy.InvokerInvocationHandler;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title:JdkProxyFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午4:07
 * @m444@126.com
 */
public class JdkProxyFactory extends AbstractProxyFactory {
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] types) {
        return (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), types, new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
                Method method = proxy.getClass().getMethod(methodName, parameterTypes);
                return method.invoke(proxy, arguments);
            }
        };
    }
}
