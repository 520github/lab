package com.sunso.lab.framework.rpc.dubbo.remoting.exchange;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.telnet.TelnetHandler;

import java.util.concurrent.CompletableFuture;

/**
 * @Title:ExchangeHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午10:22
 * @m444@126.com
 */
public interface ExchangeHandler extends ChannelHandler, TelnetHandler {

    CompletableFuture<Object> reply(ExchangeChannel channel, Object request) throws RemotingException;
}
