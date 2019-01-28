package com.sunso.lab.framework.rpc.dubbo.rpc.protocol;

import com.sunso.lab.framework.rpc.dubbo.rpc.Exporter;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;

/**
 * @Title:AbstractExporter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午5:48
 * @m444@126.com
 */
public abstract class AbstractExporter<T> implements Exporter<T> {
    private final Invoker<T> invoker;
    private volatile boolean unexported = false;

    public AbstractExporter(Invoker<T> invoker) {
        if (invoker == null) {
            throw new IllegalStateException("service invoker == null");
        }
        if (invoker.getInterface() == null) {
            throw new IllegalStateException("service type == null");
        }
        if (invoker.getUrl() == null) {
            throw new IllegalStateException("service url == null");
        }
        this.invoker = invoker;
    }

    public Invoker<T> getInvoker() {
        return invoker;
    }

    public void unexport() {
        if(unexported) {
            return;
        }
        unexported = true;
        getInvoker().destroy();
    }

    public String toString() {
        return getInvoker().toString();
    }

}
