package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Title:AsyncContextImpl
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/9下午12:15
 * @m444@126.com
 */
public class AsyncContextImpl implements AsyncContext {

    private final AtomicBoolean started = new AtomicBoolean(false);
    private final AtomicBoolean stopped = new AtomicBoolean(false);

    private CompletableFuture<Object> future;
    private RpcContext storedContext;
    private RpcContext storedServerContext;

    public AsyncContextImpl() {
    }

    public AsyncContextImpl(CompletableFuture<Object> future) {
        this.future = future;
        this.storedContext = RpcContext.getContext();
        this.storedServerContext = RpcContext.getServerContext();
    }

    @Override
    public CompletableFuture getInternalFuture() {
        return future;
    }

    @Override
    public void write(Object value) {
        if(isAsyncStarted() && stop()) {
            if(value instanceof Throwable) {
                Throwable bizExe = (Throwable) value;
                future.completeExceptionally(bizExe);
            }
            else {
                future.complete(value);
            }
        }
        else {
            throw new IllegalStateException("The async response has probably been wrote back by another thread, or the asyncContext has been closed.");
        }
    }

    @Override
    public boolean isAsyncStarted() {
        return started.get();
    }

    @Override
    public boolean stop() {
        return stopped.compareAndSet(false, true);
    }

    @Override
    public void start() {
        this.started.set(true);
    }

    @Override
    public void signalContextSwitch() {
        RpcContext.restoreContext(storedContext);
        RpcContext.restoreServerContext(storedServerContext);
        //Restore any other contexts in here if necessary.
    }
}
