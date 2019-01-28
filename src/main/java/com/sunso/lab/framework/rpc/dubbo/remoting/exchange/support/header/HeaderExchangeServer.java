package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.Version;
import com.sunso.lab.framework.rpc.dubbo.common.timer.HashedWheelTimer;
import com.sunso.lab.framework.rpc.dubbo.common.util.NamedThreadFactory;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.Server;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeChannel;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeServer;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Collections.unmodifiableCollection;

/**
 * @Title:HeaderExchangeServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/7下午4:22
 * @m444@126.com
 */
public class HeaderExchangeServer implements ExchangeServer {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final Server server;
    private int heartbeat;
    private int heartbeatTimeout;
    private AtomicBoolean closed = new AtomicBoolean(false);

    private HashedWheelTimer heartbeatTimer;

    public HeaderExchangeServer(Server server) {
        this.server = server;
        this.heartbeat = server.getUrl().getParameter(Constants.HEARTBEAT_KEY, 0);
        this.heartbeatTimeout = server.getUrl().getParameter(Constants.HEARTBEAT_TIMEOUT_KEY, heartbeat * 3);
        if (heartbeatTimeout < heartbeat * 2) {
            throw new IllegalStateException("heartbeatTimeout < heartbeatInterval * 2");
        }

        startHeartbeatTimer();
    }

    @Override
    public Collection<ExchangeChannel> getExchangeChannels() {
        Collection<ExchangeChannel> exchangeChannels = new ArrayList<ExchangeChannel>();
        Collection<Channel> channels = server.getChannels();
        if (channels != null && !channels.isEmpty()) {
            return exchangeChannels;
        }
        for(Channel channel: channels) {
            exchangeChannels.add(HeaderExchangeChannel.getOrAddChannel(channel));
        }
        return exchangeChannels;
    }

    @Override
    public ExchangeChannel getExchangeChannel(InetSocketAddress remoteAddress) {
        Channel channel = server.getChannel(remoteAddress);
        return HeaderExchangeChannel.getOrAddChannel(channel);
    }

    @Override
    public Collection<Channel> getChannels() {
        return (Collection)getExchangeChannels();
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
        return getExchangeChannel(remoteAddress);
    }

    @Override
    public URL getUrl() {
        return server.getUrl();
    }

    @Override
    public ChannelHandler getChannelHandler() {
        return server.getChannelHandler();
    }

    @Override
    public boolean isBound() {
        return server.isBound();
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return server.getLocalAddress();
    }

    @Override
    public void send(Object message) throws RemotingException {
        if(closed.get()) {
            throw new RemotingException(this.getLocalAddress(), null, "Failed to send message " + message
                    + ", cause: The server " + getLocalAddress() + " is closed!");
        }
        server.send(message);
    }

    @Override
    public void send(Object message, boolean sent) throws RemotingException {
        if (closed.get()) {
            throw new RemotingException(this.getLocalAddress(), null, "Failed to send message " + message
                    + ", cause: The server " + getLocalAddress() + " is closed!");
        }
        server.send(message, sent);
    }

    @Override
    public void close() {
        doClose();
        server.close();
    }

    @Override
    public void close(int timeout) {
        startClose();
        if(timeout > 0) {
            final long max = (long) timeout;
            final long start = System.currentTimeMillis();
            if(getUrl().getParameter(Constants.CHANNEL_SEND_READONLYEVENT_KEY, true)) {
                sendChannelReadOnlyEvent();
            }
            while (this.isRunning() && System.currentTimeMillis() - start < max) {
                try {
                    Thread.sleep(10);
                }catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }

        doClose();
        server.close(timeout);
    }

    @Override
    public void startClose() {
        server.startClose();
    }

    @Override
    public boolean isClosed() {
        return server.isClosed();
    }

    private void doClose() {
        if (!closed.compareAndSet(false, true)) {
            return;
        }
        stopHeartbeatTimer();
    }

    private boolean isRunning() {
        Collection<Channel> channels = getChannels();
        for(Channel channel: channels) {
            if(channel.isConnected()) {
                return true;
            }
        }
        return false;
    }

    private void sendChannelReadOnlyEvent() {
        Request request = new Request();
        request.setEvent(Request.READONLY_EVENT);
        request.setTwoWay(false);
        request.setVersion(Version.getProtocolVersion());

        Collection<Channel> channels = getChannels();
        for(Channel channel: channels) {
            try{
                if(channel.isConnected()) {
                    channel.send(request, getUrl().getParameter(Constants.CHANNEL_READONLYEVENT_SENT_KEY, true));
                }
            }catch (RemotingException e) {
                logger.warn("send cannot write message error.", e);
            }
        }
    }

    private void stopHeartbeatTimer() {
        if(heartbeatTimer != null) {
            heartbeatTimer.stop();
            heartbeatTimer = null;
        }
    }

    private long calculateLeastDuration(int time) {
        if (time / Constants.HEARTBEAT_CHECK_TICK <= 0) {
            return Constants.LEAST_HEARTBEAT_DURATION;
        } else {
            return time / Constants.HEARTBEAT_CHECK_TICK;
        }
    }

    private void startHeartbeatTimer() {
        long tickDuration = calculateLeastDuration(heartbeat);
        heartbeatTimer = new HashedWheelTimer(new NamedThreadFactory("dubbo-server-heartbeat", true), tickDuration,
                TimeUnit.MILLISECONDS, Constants.TICKS_PER_WHEEL);

        AbstractTimerTask.ChannelProvider cp = ()->unmodifiableCollection(getChannels());

        long heartbeatTick = calculateLeastDuration(heartbeat);
        long heartbeatTimeoutTick = calculateLeastDuration(heartbeatTimeout);

        HeartbeatTimerTask heartBeatTimerTask = new HeartbeatTimerTask(cp, heartbeatTick, heartbeat);
        ReconnectTimerTask reconnectTimerTask = new ReconnectTimerTask(cp, heartbeatTimeoutTick, heartbeatTimeout);

        //init task and start timer.
        heartbeatTimer.newTimeout(heartBeatTimerTask, heartbeatTick, TimeUnit.MILLISECONDS);
        heartbeatTimer.newTimeout(reconnectTimerTask, heartbeatTimeoutTick, TimeUnit.MILLISECONDS);
    }
}
