package com.sunso.lab.framework.rpc.dubbo.remoting.exchange;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;

/**
 * @Title:ExchangeChannel
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午10:19
 * @m444@126.com
 */
public interface ExchangeChannel extends Channel {
    ResponseFuture request(Object request)  throws RemotingException;

    ResponseFuture request(Object request, int timeout)  throws RemotingException ;

    ExchangeHandler getExchangeHandler();

    @Override
    void close(int timeout);
}
