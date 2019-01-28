package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

/**
 * @Title:AsyncRpcResult
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/9下午3:27
 * @m444@126.com
 */
public class AsyncRpcResult extends AbstractResult {

    private RpcContext storedContext;
    private RpcContext storedServerContext;

    protected CompletableFuture<Object> valueFuture;
    protected CompletableFuture<Result> resultFuture;

    public AsyncRpcResult(CompletableFuture<Object> future) {
        this(future, true);
    }

    public AsyncRpcResult(CompletableFuture<Object> future, boolean registerCallback) {
        this(future, new CompletableFuture<>(), registerCallback);
    }

    public AsyncRpcResult(CompletableFuture<Object> future,
                          final CompletableFuture<Result> rFuture,
                          boolean registerCallback) {
        if (rFuture == null) {
            throw new IllegalArgumentException();
        }

        resultFuture = rFuture;
        if(registerCallback) {
            future.whenComplete((v, t) -> {
                RpcResult rpcResult;
                if(t != null) {
                    if( t instanceof CompletionException) {
                        rpcResult = new RpcResult(t.getCause());
                    }
                    else {
                        rpcResult = new RpcResult(t);
                    }
                }
                else {
                    rpcResult = new RpcResult(v);
                }
                // instead of resultFuture we must use rFuture here, resultFuture may being changed before complete when building filter chain, but rFuture was guaranteed never changed by closure.
                rFuture.complete(rpcResult);
            });
        }

        this.valueFuture = future;
        this.storedContext = RpcContext.getContext().copyOf();
        this.storedServerContext = RpcContext.getServerContext().copyOf();
    }

    @Override
    public Object getValue() {
        return getRpcResult().getValue();
    }

    @Override
    public Throwable getException() {
        return getRpcResult().getException();
    }

    @Override
    public boolean hasException() {
        return getRpcResult().hasException();
    }

    @Override
    public Object recreate() throws Throwable {
        return valueFuture;
    }

    @Override
    public Object getResult() {
        return getRpcResult().getResult();
    }

    public Result getRpcResult() {
        Result result;
        try{
            result = resultFuture.get();
        }catch (Exception e) {
            result = new RpcResult();
        }
        return result;
    }

    public CompletableFuture<Result> getResultFuture() {
        return resultFuture;
    }
}
