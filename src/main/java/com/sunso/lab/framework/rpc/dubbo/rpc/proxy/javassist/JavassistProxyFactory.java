package com.sunso.lab.framework.rpc.dubbo.rpc.proxy.javassist;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.bytecode.Proxy;
import com.sunso.lab.framework.rpc.dubbo.common.bytecode.Wrapper;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.proxy.AbstractProxyFactory;
import com.sunso.lab.framework.rpc.dubbo.rpc.proxy.AbstractProxyInvoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.proxy.InvokerInvocationHandler;

/**
 * @Title:JavassistProxyFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午3:29
 * @m444@126.com
 */
public class JavassistProxyFactory extends AbstractProxyFactory {
    @Override
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));
    }

    @Override
    public <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url) {
        final Wrapper wrapper = Wrapper.getWrapper(proxy.getClass().getName().indexOf('$') < 0 ? proxy.getClass() : type);
        return new AbstractProxyInvoker<T>(proxy, type, url) {
            @Override
            protected Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable {
                return wrapper.invokeMethod(proxy, methodName, parameterTypes, arguments);
            }
        };
    }
}
