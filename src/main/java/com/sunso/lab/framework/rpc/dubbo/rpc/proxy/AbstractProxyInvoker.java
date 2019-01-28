package com.sunso.lab.framework.rpc.dubbo.rpc.proxy;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.rpc.*;
import com.sunso.lab.framework.rpc.dubbo.rpc.support.RpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

/**
 * @Title:AbstractProxyInvoker
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午3:34
 * @m444@126.com
 */
public abstract class AbstractProxyInvoker<T> implements Invoker<T> {

    Logger logger = LoggerFactory.getLogger(AbstractProxyInvoker.class);

    private final T proxy;
    private final Class<T> type;
    private final URL url;

    public AbstractProxyInvoker(T proxy, Class<T> type, URL url) {
        if (proxy == null) {
            throw new IllegalArgumentException("proxy == null");
        }
        if (type == null) {
            throw new IllegalArgumentException("interface == null");
        }
        if(!type.isInstance(proxy)) {
            throw new IllegalArgumentException(proxy.getClass().getName() + " not implement interface " + type);
        }
        this.proxy = proxy;
        this.type = type;
        this.url = url;
    }

    @Override
    public Class<T> getInterface() {
        return type;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String toString() {
        return getInterface() + " -> " + (getUrl() == null ? " " : getUrl().toString());
    }

    @Override
    public void destroy() {
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        try{
            Object obj = doInvoke(proxy, invocation.getMethodName(), invocation.getParameterTypes(), invocation.getArguments());
            if (RpcUtils.isFutureReturnType(invocation)) {
                return new AsyncRpcResult((CompletableFuture<Object>) obj);
            }
            else if(rpcContext.isAsyncStarted()) {
                return new AsyncRpcResult(rpcContext.getAsyncContext().getInternalFuture());
            }
            else {
                return new RpcResult(obj);
            }
        } catch (InvocationTargetException e) {
            if (rpcContext.isAsyncStarted() && !rpcContext.stopAsync()) {
                logger.error("Provider async started, but got an exception from the original method, cannot write the exception back to consumer because an async result may have returned the new thread.", e);
            }
            return new RpcResult(e.getTargetException());
        } catch (Throwable e) {
            throw new RpcException("Failed to invoke remote proxy method " + invocation.getMethodName() + " to " + getUrl() + ", cause: " + e.getMessage(), e);
        }
    }

    protected abstract Object doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Throwable;

}
