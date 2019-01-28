package com.sunso.lab.framework.rpc.dubbo.rpc;

import com.sunso.lab.framework.rpc.dubbo.common.Node;

/**
 * @Title:Invoker
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午3:41
 * @m444@126.com
 */
public interface Invoker<T> extends Node {

    Class<T> getInterface();

    Result invoke(Invocation invocation) throws RpcException;
}
