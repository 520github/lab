package com.sunso.lab.framework.rpc.dubbo.remoting.exchange;

import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;

/**
 * @Title:ResponseFuture
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午10:08
 * @m444@126.com
 */
public interface ResponseFuture {
    Object get() throws RemotingException;

    Object get(int timeoutInMillis) throws RemotingException;

    void setCallback(ResponseCallback callback);

    boolean isDone();


}
