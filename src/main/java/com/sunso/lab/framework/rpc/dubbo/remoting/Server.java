package com.sunso.lab.framework.rpc.dubbo.remoting;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @Title:Server
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1上午11:28
 * @m444@126.com
 */
public interface Server extends Endpoint {

    boolean isBound();

    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress remoteAddress);
}

