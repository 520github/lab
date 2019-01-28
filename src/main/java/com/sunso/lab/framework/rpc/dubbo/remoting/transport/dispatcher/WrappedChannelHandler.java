package com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.ChannelHandlerDelegate;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title:WrappedChannelHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午3:41
 * @m444@126.com
 */
public class WrappedChannelHandler implements ChannelHandlerDelegate {

    protected static final ExecutorService SHARED_EXECUTOR = Executors.newCachedThreadPool();

    protected final ChannelHandler handler;
    protected final URL url;

    protected final ExecutorService executor;

    public WrappedChannelHandler(ChannelHandler handler, URL url) {
        this.handler = handler;
        this.url = url;
        executor = null;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public ChannelHandler getHandler() {
        if(handler instanceof ChannelHandlerDelegate) {
            return ((ChannelHandlerDelegate)handler).getHandler();
        }
        return handler;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        handler.connected(channel);
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        handler.disconnected(channel);
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        handler.sent(channel, message);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        handler.received(channel, message);
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        handler.caught(channel, exception);
    }

    public ExecutorService getExecutorService() {
        ExecutorService executorService = executor;
        if(executorService == null || executorService.isShutdown()) {
            executorService = SHARED_EXECUTOR;
        }
        return executorService;
    }
}
