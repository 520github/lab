package com.sunso.lab.framework.rpc.dubbo.remoting.transport.netty4;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title:NettyServerHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2上午11:03
 * @m444@126.com
 */
@io.netty.channel.ChannelHandler.Sharable
public class NettyServerHandler extends ChannelDuplexHandler {
    private final Map<String, Channel> channels = new ConcurrentHashMap<>();
    private final URL url;
    private final ChannelHandler handler;

    public NettyServerHandler(URL url, ChannelHandler handler) {
        if(url == null) {
            throw new IllegalArgumentException("url == null");
        }
        if(handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        this.url = url;
        this.handler = handler;
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) {

    }
}
