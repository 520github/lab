package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Title:ChannelHandlerDispatcher
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午3:41
 * @m444@126.com
 */
public class ChannelHandlerDispatcher implements ChannelHandler {
    private static final Logger logger = LoggerFactory.getLogger(ChannelHandlerDispatcher.class);
    private final Collection<ChannelHandler> channelHandlers = new CopyOnWriteArraySet<ChannelHandler>();

    public ChannelHandlerDispatcher() {
    }

    public ChannelHandlerDispatcher(ChannelHandler... handlers) {
        this(handlers == null ? null : Arrays.asList(handlers));
    }

    public ChannelHandlerDispatcher(Collection<ChannelHandler> handlers) {
        if (handlers != null && !handlers.isEmpty()) {
            this.channelHandlers.addAll(handlers);
        }
    }

    public Collection<ChannelHandler> getChannelHandlers() {
        return channelHandlers;
    }

    public ChannelHandlerDispatcher addChannelHandler(ChannelHandler handler) {
        this.channelHandlers.add(handler);
        return this;
    }

    public ChannelHandlerDispatcher removeChannelHandler(ChannelHandler handler) {
        this.channelHandlers.remove(handler);
        return this;
    }


    @Override
    public void connected(Channel channel) throws RemotingException {
        for(ChannelHandler listener: channelHandlers) {
            try{
                listener.connected(channel);
            }catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        for (ChannelHandler listener : channelHandlers) {
            try{
            }catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        for (ChannelHandler listener : channelHandlers) {
            try {
                listener.sent(channel, message);
            } catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        for (ChannelHandler listener : channelHandlers) {
            try {
                listener.received(channel, message);
            } catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        for (ChannelHandler listener : channelHandlers) {
            try {
                listener.caught(channel, exception);
            } catch (Throwable t) {
                logger.error(t.getMessage(), t);
            }
        }
    }
}
