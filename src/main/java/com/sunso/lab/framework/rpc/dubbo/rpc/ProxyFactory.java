package com.sunso.lab.framework.rpc.dubbo.rpc;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:ProxyFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午4:58
 * @m444@126.com
 */
public interface ProxyFactory {

    <T> T getProxy(Invoker<T> invoker) throws RpcException;

    <T> T getProxy(Invoker<T> invoker, boolean generic) throws RpcException;

    <T> Invoker<T> getInvoker(T proxy, Class<T> type, URL url);
}
