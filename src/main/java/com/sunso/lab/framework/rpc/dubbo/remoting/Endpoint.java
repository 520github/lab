package com.sunso.lab.framework.rpc.dubbo.remoting;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.net.InetSocketAddress;

/**
 * @Title:Endpoint
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午12:09
 * @m444@126.com
 */
public interface Endpoint {

    URL getUrl();

    ChannelHandler getChannelHandler();

    InetSocketAddress getLocalAddress();

    void send(Object message) throws RemotingException ;

    void send(Object message, boolean sent) throws RemotingException;

    void close();

    void close(int timeout);

    void startClose();

    boolean isClosed();

}
