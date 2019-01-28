package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support;

import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeChannel;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.telnet.support.TelnetHandlerAdapter;

import java.util.concurrent.CompletableFuture;

/**
 * @Title:ExchangeHandlerAdapter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午4:04
 * @m444@126.com
 */
public abstract class ExchangeHandlerAdapter extends TelnetHandlerAdapter implements ExchangeHandler {

    @Override
    public CompletableFuture<Object> reply(ExchangeChannel channel, Object msg) throws RemotingException {
        return null;
    }
}
