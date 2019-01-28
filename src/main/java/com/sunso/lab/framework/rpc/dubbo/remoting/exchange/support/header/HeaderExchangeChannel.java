package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.*;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.Version;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.DefaultFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @Title:HeaderExchangeChannel
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午10:26
 * @m444@126.com
 */
public class HeaderExchangeChannel implements ExchangeChannel {

    private static final Logger logger = LoggerFactory.getLogger(HeaderExchangeChannel.class);

    private static final String CHANNEL_KEY = HeaderExchangeChannel.class.getName() + ".CHANNEL";
    private final Channel channel;
    private volatile boolean closed = false;

    HeaderExchangeChannel(Channel channel) {
        if (channel == null) {
            throw new IllegalArgumentException("channel == null");
        }
        this.channel = channel;
    }

    static HeaderExchangeChannel getOrAddChannel(Channel ch) {
        if(ch == null) {
            return null;
        }
        HeaderExchangeChannel ret = (HeaderExchangeChannel)ch.getAttribute(CHANNEL_KEY);
        if(ret == null) {
            ret = new HeaderExchangeChannel(ch);
            if(ch.isConnected()) {
                ch.setAttribute(CHANNEL_KEY, ret);
            }
        }
        return ret;
    }

    static void removeChannelIfDisconnected(Channel ch) {
        if(ch != null && !ch.isConnected()) {
            ch.removeAttribute(CHANNEL_KEY);
        }
    }

    @Override
    public ResponseFuture request(Object request) throws RemotingException   {
        return request(request, channel.getUrl().getPositiveParameter(Constants.TIMEOUT_KEY, Constants.DEFAULT_TIMEOUT));
    }

    @Override
    public ResponseFuture request(Object request, int timeout)  throws RemotingException  {
        if(closed) {
            throw new RemotingException(this.getLocalAddress(), null, "Failed to send request " + request + ", cause: The channel " + this + " is closed!");
        }
        Request req = new Request();
        req.setVersion(Version.getProtocolVersion());
        req.setTwoWay(true);
        req.setData(request);
        DefaultFuture future = DefaultFuture.newFuture(channel, req, timeout);
        try{
            channel.send(req);
        }catch (RemotingException e) {
            future.cancel();
            throw e;
        }
        return future;
    }

    @Override
    public ExchangeHandler getExchangeHandler() {
        return (ExchangeHandler) channel.getChannelHandler();
    }

    @Override
    public URL getUrl() {
        return channel.getUrl();
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return channel.getChannelHandler();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return channel.getLocalAddress();
    }

    @Override
    public void send(Object message) throws RemotingException  {
        send(message, false);
    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        if(closed) {
            throw new RemotingException(this.getLocalAddress(), null, "Failed to send message " + message + ", cause: The channel " + this + " is closed!");
        }
        if(message instanceof Request
                || message instanceof Response
                || message instanceof String) {
            channel.send(message, sent);
        }
        else {
            Request request = new Request();
            request.setVersion(Version.getProtocolVersion());
            request.setTwoWay(false);
            request.setData(message);
            channel.send(message, sent);
        }
    }

    @Override
    public void close() {
        try{
            channel.close();
        }catch (Throwable e) {
            logger.warn(e.getMessage(), e);
        }
    }

    @Override
    public void close(int timeout) {
        if(closed) {
            return;
        }
        closed = true;
        if(timeout > 0) {
            long start = System.currentTimeMillis();
            while(DefaultFuture.hasFuture(channel) && System.currentTimeMillis() - start < timeout) {
                try{
                    Thread.sleep(10);
                }catch (Throwable e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }
        close();
    }

    @Override
    public void startClose() {
        channel.startClose();
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return channel.getRemoteAddress();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean hasAttribute(String key) {
        return channel.hasAttribute(key);
    }

    @Override
    public Object getAttribute(String key) {
        return channel.getAttribute(key);
    }

    @Override
    public void setAttribute(String key, Object value) {
        channel.setAttribute(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        channel.removeAttribute(key);
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((channel == null) ? 0 : channel.hashCode());
        return result;
    }

    public String toString() {
        return channel.toString();
    }

    public boolean equals(Object object) {
        if(this == object) {
            return true;
        }
        if(object == null) {
            return false;
        }
        if(getClass() != object.getClass()) {
            return false;
        }
        HeaderExchangeChannel other = (HeaderExchangeChannel) object;
        if(channel == null) {
            if(other.channel != null) {
                return false;
            }
        }
        else if (!channel.equals(other.channel)){
            return false;
        }
        return true;
    }
}
