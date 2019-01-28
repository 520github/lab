package com.sunso.lab.framework.rpc.dubbo.rpc;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:Protocol
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午4:18
 * @m444@126.com
 */
public interface Protocol {

    int getDefaultPort();


    <T> Exporter<T> export(Invoker<T> invoker) throws RpcException;

    <T> Invoker<T> refer(Class<T> type, URL url) throws RpcException;

    void destroy();
}
