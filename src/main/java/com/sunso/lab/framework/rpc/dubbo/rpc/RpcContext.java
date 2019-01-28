package com.sunso.lab.framework.rpc.dubbo.rpc;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.threadlocal.InternalThreadLocal;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @Title:RpcContext
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午4:57
 * @m444@126.com
 */
public class RpcContext {

    private AsyncContext asyncContext;
    private InetSocketAddress localAddress;
    private InetSocketAddress remoteAddress;

    private final Map<String, String> attachments = new HashMap<String, String>();
    private final Map<String, Object> values = new HashMap<String, Object>();
    private List<URL> urls;
    private URL url;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;
    private Future<?> future;

    @Deprecated
    private List<Invoker<?>> invokers;
    @Deprecated
    private Invoker<?> invoker;
    @Deprecated
    private Invocation invocation;

    // now we don't use the 'values' map to hold these objects
    // we want these objects to be as generic as possible
    private Object request;
    private Object response;


    private static final InternalThreadLocal<RpcContext> LOCAL = new InternalThreadLocal<RpcContext>() {
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };

    private static final InternalThreadLocal<RpcContext> SERVER_LOCAL = new InternalThreadLocal<RpcContext>() {
        @Override
        protected RpcContext initialValue() {
            return new RpcContext();
        }
    };

    public RpcContext copyOf() {
        RpcContext copy = new RpcContext();
        copy.attachments.putAll(this.attachments);
        copy.values.putAll(this.values);
        copy.future = this.future;
        copy.urls = this.urls;
        copy.url = this.url;
        copy.methodName = this.methodName;
        copy.parameterTypes = this.parameterTypes;
        copy.arguments = this.arguments;
        copy.localAddress = this.localAddress;
        copy.remoteAddress = this.remoteAddress;
        copy.invokers = this.invokers;
        copy.invoker = this.invoker;
        copy.invocation = this.invocation;

        copy.request = this.request;
        copy.response = this.response;
        copy.asyncContext = this.asyncContext;

        return copy;
    }


    /**
     * get context.
     *
     * @return context
     */
    public static RpcContext getContext() {
        return LOCAL.get();
    }

    public static RpcContext getServerContext() {
        return SERVER_LOCAL.get();
    }

    public static void restoreContext(RpcContext oldContext) {
        LOCAL.set(oldContext);
    }

    public static void restoreServerContext(RpcContext oldServerContext) {
        SERVER_LOCAL.set(oldServerContext);
    }

    public void setAsyncContext(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    public AsyncContext getAsyncContext() {
        return asyncContext;
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public RpcContext setLocalAddress(InetSocketAddress localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public RpcContext setRemoteAddress(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public boolean isAsyncStarted() {
        if (this.asyncContext == null) {
            return false;
        }
        return asyncContext.isAsyncStarted();
    }

    public boolean stopAsync() {
        return asyncContext.stop();
    }
}
