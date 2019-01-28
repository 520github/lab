package com.sunso.lab.framework.rpc.dubbo.remoting.transport.netty4;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.AbstractChannel;
import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Title:NettyChannel
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午8:45
 * @m444@126.com
 */
final public class NettyChannel extends AbstractChannel {

    private final Channel channel;
    private static final ConcurrentMap<Channel, NettyChannel> channelMap = new ConcurrentHashMap<Channel, NettyChannel>();
    private final Map<String, Object> attributes = new ConcurrentHashMap<String, Object>();

    private NettyChannel(Channel channel, URL url, ChannelHandler handler) {
        super(url, handler);
        if(channel == null) {
            throw new IllegalArgumentException("netty channel == null;");
        }
        this.channel = channel;
    }

    static NettyChannel getOrAddChannel(Channel ch, URL url, ChannelHandler handler) {
        if(ch == null) {
            return null;
        }
        NettyChannel nettyChannel = channelMap.get(ch);
        if(nettyChannel == null) {
            NettyChannel nc = new NettyChannel(ch, url, handler);
            if(ch.isActive()) {
                nettyChannel = channelMap.putIfAbsent(ch, nc);
            }
            if(nettyChannel == null) {
                nettyChannel = nc;
            }
        }
        return nettyChannel;
    }

    static void removeChannelIfDisconnected(Channel ch) {
        if(ch != null && !ch.isActive()) {
            channelMap.remove(ch);
        }
    }

    public void send(Object message, boolean sent) throws RemotingException {
        super.send(message, sent);

        boolean success = true;
        int timeout = 0;

        try{
            ChannelFuture future = channel.writeAndFlush(message);
            if(sent) {
                timeout = getUrl().getPositiveParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT);
                success = future.await(timeout);
            }
            Throwable cause = future.cause();
            if(cause != null) {
                throw cause;
            }
        }catch (Throwable e) {
            throw new RemotingException(this, "Failed to send message " + message + " to " + getRemoteAddress() + ", cause: " + e.getMessage(), e);
        }
        if (!success) {
            throw new RemotingException(this, "Failed to send message " + message + " to " + getRemoteAddress()
                    + "in timeout(" + timeout + "ms) limit");
        }
    }


    @Override
    public InetSocketAddress getRemoteAddress() {
        return (InetSocketAddress)channel.remoteAddress();
    }

    @Override
    public boolean isConnected() {
        return !isClosed() && channel.isActive();
    }

    @Override
    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    @Override
    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        if (value == null) { // The null value unallowed in the ConcurrentHashMap.
            attributes.remove(key);
        } else {
            attributes.put(key, value);
        }
    }

    @Override
    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress)channel.localAddress();
    }
}
