package com.sunso.lab.framework.rpc.dubbo.remoting.exchange;

import com.sunso.lab.framework.rpc.dubbo.remoting.Server;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @Title:ExchangeServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/4下午7:20
 * @m444@126.com
 */
public interface ExchangeServer extends Server {

    Collection<ExchangeChannel> getExchangeChannels();

    ExchangeChannel getExchangeChannel(InetSocketAddress remoteAddress);
}
