package com.sunso.lab.framework.rpc.dubbo.config.invoker;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.config.ServiceConfig;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invocation;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.Result;
import com.sunso.lab.framework.rpc.dubbo.rpc.RpcException;

/**
 * @Title:DelegateProviderMetaDataInvoker
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午7:34
 * @m444@126.com
 */
public class DelegateProviderMetaDataInvoker<T> implements Invoker {

    protected final Invoker<T> invoker;
    private ServiceConfig metadata;

    public DelegateProviderMetaDataInvoker(Invoker<T> invoker,ServiceConfig metadata) {
        this.invoker = invoker;
        this.metadata = metadata;
    }

    @Override
    public Class getInterface() {
        return invoker.getInterface();
    }

    @Override
    public Result invoke(Invocation invocation) throws RpcException {
        return invoker.invoke(invocation);
    }

    @Override
    public URL getUrl() {
        return invoker.getUrl();
    }

    @Override
    public boolean isAvailable() {
        return invoker.isAvailable();
    }

    @Override
    public void destroy() {
        invoker.destroy();
    }

    public ServiceConfig getMetadata() {
        return metadata;
    }
}
