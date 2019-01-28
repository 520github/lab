package com.sunso.lab.framework.rpc.dubbo.remoting;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.ChannelHandlerDispatcher;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.netty4.NettyTransporter;

/**
 * @Title:Transporters
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午3:38
 * @m444@126.com
 */
public class Transporters  {

    private Transporters() {
    }


    public static Server bind(URL url, ChannelHandler... handlers) throws RemotingException {
        if (url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if (handlers == null || handlers.length == 0) {
            throw new IllegalArgumentException("handlers == null");
        }

        ChannelHandler handler;
        if(handlers.length == 1) {
            handler = handlers[0];
        }
        else {
            handler = new ChannelHandlerDispatcher(handlers);
        }
        return getTransporter().bind(url, handler);
    }


    public static Transporter getTransporter() {
        return new NettyTransporter();
    }
}
