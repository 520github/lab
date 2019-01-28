package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.util.concurrent.CompletableFuture;

/**
 * @Title:AsyncContext
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/9下午12:13
 * @m444@126.com
 */
public interface AsyncContext {
    CompletableFuture getInternalFuture();

    void write(Object value);

    boolean isAsyncStarted();

    boolean stop();

    void start();

    void signalContextSwitch();
}
