package com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:ChannelEventRunnable
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午3:33
 * @m444@126.com
 */
public class ChannelEventRunnable implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ChannelEventRunnable.class);

    private final ChannelHandler handler;
    private final Channel channel;
    private final ChannelState state;
    private final Throwable exception;
    private final Object message;

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state) {
        this(channel, handler, state, null);
    }

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state, Object message) {
        this(channel, handler, state, message, null);
    }

    public ChannelEventRunnable(Channel channel, ChannelHandler handler, ChannelState state, Throwable t) {
        this(channel, handler, state, null, t);
    }

    public ChannelEventRunnable(
            Channel channel, ChannelHandler handler, ChannelState state, Object message, Throwable exception) {
        this.channel = channel;
        this.handler = handler;
        this.state = state;
        this.message = message;
        this.exception = exception;
    }

    @Override
    public void run() {
        handleState();
    }

    private void handleState() {
        try{
            switch (state) {
                case CONNECTED:
                    handler.connected(channel);
                    break;
                case RECEIVED:
                    handler.received(channel, message);
                    break;
                case SENT:
                    handler.sent(channel, message);
                    break;
                case DISCONNECTED:
                    handler.disconnected(channel);
                    break;
                case CAUGHT:
                    handler.caught(channel, exception);
                    break;
                default:
                    logger.warn("unknown state: " + state + ", message is " + message);
            }
        }catch (Exception e) {
            logException(e);
        }
    }

    private void logException(Exception e) {
        String msg = "ChannelEventRunnable handle " + state + " operation error, channel is " + channel;
        if(message != null) {
            msg = msg + ", message is " + message;
        }
        if(exception != null) {
            msg = msg + ", exception is " + exception;
        }
        logger.warn(msg, e);
    }

    public enum ChannelState {
        CONNECTED,
        DISCONNECTED,
        SENT,
        RECEIVED,
        CAUGHT
    }
}
