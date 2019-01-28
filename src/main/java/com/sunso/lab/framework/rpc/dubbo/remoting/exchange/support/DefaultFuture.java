package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support;

import com.sunso.lab.framework.rpc.dubbo.common.timer.HashedWheelTimer;
import com.sunso.lab.framework.rpc.dubbo.common.timer.Timeout;
import com.sunso.lab.framework.rpc.dubbo.common.timer.Timer;
import com.sunso.lab.framework.rpc.dubbo.common.timer.TimerTask;
import com.sunso.lab.framework.rpc.dubbo.common.util.NamedThreadFactory;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.TimeoutException;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Response;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ResponseCallback;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ResponseFuture;
import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title:DefaultFuture
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午5:42
 * @m444@126.com
 */
public class DefaultFuture implements ResponseFuture {
    private static final Logger logger = LoggerFactory.getLogger(DefaultFuture.class);
    private static final Map<Long, Channel> CHANNELS = new ConcurrentHashMap<>();
    private static final Map<Long, DefaultFuture> FUTURES = new ConcurrentHashMap<>();
    public static final Timer TIME_OUT_TIMER = new HashedWheelTimer(
            new NamedThreadFactory("dubbo-future-timeout", true),
            30, TimeUnit.MILLISECONDS);

    private final long id;
    private final Channel channel;
    private final Request request;
    private final int timeout;
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final long start = System.currentTimeMillis();
    private volatile Response response;
    private volatile long sent;

    private DefaultFuture(Channel channel, Request request, int timeout) {
        this.channel = channel;
        this.request = request;
        this.timeout = timeout;
        this.id = request.getId();

        FUTURES.put(id, this);
        CHANNELS.put(id, channel);
    }

    private void doSent() {
        sent = System.currentTimeMillis();
    }

    private static void timeoutCheck(DefaultFuture future) {
        TimeoutCheckTask task = new TimeoutCheckTask(future);
        TIME_OUT_TIMER.newTimeout(task, future.getTimeout(), TimeUnit.MILLISECONDS);
    }

    public static DefaultFuture newFuture(Channel channel, Request request, int timeout) {
        final DefaultFuture future = new DefaultFuture(channel, request, timeout);
        timeoutCheck(future);
        return future;
    }

    public static void closeChannel(Channel channel) {
        for (Map.Entry<Long, Channel> entry: CHANNELS.entrySet()) {
            if (channel.equals(entry.getValue())) {
                DefaultFuture future = getFuture(entry.getKey());
                if (future != null && !future.isDone()) {
                    Response disconnectResponse = new Response(future.getId());
                    disconnectResponse.setStatus(Response.CHANNEL_INACTIVE);
                    disconnectResponse.setErrorMessage("Channel " +
                            channel +
                            " is inactive. Directly return the unFinished request : " +
                            future.getRequest());
                    DefaultFuture.received(channel, disconnectResponse);
                }
            }
        }
    }

    public static DefaultFuture getFuture(long id) {
        return FUTURES.get(id);
    }

    public static boolean hasFuture(Channel channel) {
        return CHANNELS.containsValue(channel);
    }

    public static void sent(Channel channel, Request request) {
        DefaultFuture future = FUTURES.get(request.getId());
        if (future != null) {
            future.doSent();
        }
    }

    public static void received(Channel channel, Response response) {
        try{
            DefaultFuture future = FUTURES.remove(response.getId());
            if(future != null) {
                future.doReceived(response);
            }
            else {
                logger.warn("The timeout response finally returned at "
                        + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()))
                        + ", response " + response
                        + (channel == null ? "" : ", channel: " + channel.getLocalAddress()
                        + " -> " + channel.getRemoteAddress()));
            }
        }finally {
            CHANNELS.remove(response.getId());
        }
    }

    public Request getRequest() {
        return request;
    }

    @Override
    public Object get() throws RemotingException {
        return get(timeout);
    }

    @Override
    public Object get(int timeout) throws RemotingException {
        if (timeout <= 0) {
            timeout = Constants.DEFAULT_TIMEOUT;
        }
        if(!isDone()) {
            long start = System.currentTimeMillis();
            lock.lock();
            try{
                while(!isDone()) {
                    done.await(timeout, TimeUnit.MILLISECONDS);
                    if(isDone() || System.currentTimeMillis() - start > timeout) {
                        break;
                    }
                }
            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                lock.unlock();
            }
            if(!isDone()) {
                throw new TimeoutException(sent > 0, channel, getTimeoutMessage(false));
            }
        }
        return returnFromResponse();
    }

    @Override
    public void setCallback(ResponseCallback callback) {

    }

    @Override
    public boolean isDone() {
        return response != null;
    }

    public void cancel() {
        Response res = new Response(id);
        res.setErrorMessage("request future has been canceled.");
        response = res;
        FUTURES.remove(id);
        CHANNELS.remove(id);
    }

    private boolean isSent() {
        return sent > 0;
    }

    private Object returnFromResponse() throws RemotingException {
        Response res = response;
        if(res == null) {
            throw new IllegalStateException("response cannot be null");
        }
        if(res.getStatus() == Response.OK) {
            return res.getResult();
        }
        if(res.getStatus() == Response.CLIENT_TIMEOUT
                || res.getStatus() == Response.SERVER_TIMEOUT) {
            throw new TimeoutException(res.getStatus() == Response.SERVER_TIMEOUT, channel, res.getErrorMessage());
        }
        throw new RemotingException(channel, res.getErrorMessage());
    }

    private String getTimeoutMessage(boolean scan) {
        long nowTimestamp = System.currentTimeMillis();
        return (sent > 0 ? "Waiting server-side response timeout" : "Sending request timeout in client-side")
                + (scan ? " by scan Timer" : "") + ". start time: "
                + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(start))) + ", end time: "
                + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) + ","
                + (sent > 0 ? " client elapsed: " + (sent - start)
                + " ms, server elapsed: " + (nowTimestamp - sent)
                : " elapsed: " + (nowTimestamp - start)) + " ms, timeout: "
                + timeout + " ms, request: " + request + ", channel: " + channel.getLocalAddress()
                + " -> " + channel.getRemoteAddress();
    }

    public long getId() {
        return id;
    }

    public int getTimeout() {
        return timeout;
    }

    public Channel getChannel() {
        return channel;
    }

    private void doReceived(Response res) {
        lock.lock();
        try {
            response = res;
            if(done != null) {
                done.signal();
            }
        }finally {
            lock.unlock();
        }

    }

    private static class TimeoutCheckTask implements TimerTask {

        private DefaultFuture future;

        TimeoutCheckTask(DefaultFuture future) {
            this.future = future;
        }

        @Override
        public void run(Timeout timeout) throws Exception {
            if(future == null || future.isDone()) {
                return;
            }
            Response response = new Response(future.getId());
            response.setStatus(future.isSent() ? Response.SERVER_TIMEOUT : Response.CLIENT_TIMEOUT);
            response.setErrorMessage(future.getTimeoutMessage(true));
            DefaultFuture.received(future.getChannel(), response);
        }
    }
}
