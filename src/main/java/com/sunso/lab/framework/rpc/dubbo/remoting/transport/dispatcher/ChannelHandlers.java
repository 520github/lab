package com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.Dispatcher;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header.HeartbeatHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.MultiMessageHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher.all.AllDispatcher;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:ChannelHandlers
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午5:25
 * @m444@126.com
 */
public class ChannelHandlers {

    private static ChannelHandlers INSTANCE = new ChannelHandlers();

    protected ChannelHandlers() {
    }

    public static ChannelHandler wrap(ChannelHandler handler, URL url) {
        return ChannelHandlers.getInstance().wrapInternal(handler, url);
    }

    protected static ChannelHandlers getInstance() {
        return INSTANCE;
    }

    private ChannelHandler wrapInternal(ChannelHandler handler, URL url) {
        return new MultiMessageHandler(new HeartbeatHandler(getDispatcher().dispatch(handler, url)));
    }

    private static Dispatcher getDispatcher() {
        return new AllDispatcher();
    }
}
