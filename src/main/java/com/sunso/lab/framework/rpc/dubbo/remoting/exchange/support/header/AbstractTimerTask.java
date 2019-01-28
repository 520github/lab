package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.common.timer.Timeout;
import com.sunso.lab.framework.rpc.dubbo.common.timer.Timer;
import com.sunso.lab.framework.rpc.dubbo.common.timer.TimerTask;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @Title:AbstractTimerTask
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/7下午5:23
 * @m444@126.com
 */
public abstract class AbstractTimerTask implements TimerTask {

    private final ChannelProvider channelProvider;
    private final Long tick;

    AbstractTimerTask(ChannelProvider channelProvider, Long tick) {
        if (channelProvider == null || tick == null) {
            throw new IllegalArgumentException();
        }
        this.tick = tick;
        this.channelProvider = channelProvider;
    }

    static Long lastRead(Channel channel) {
        return (Long) channel.getAttribute(HeaderExchangeHandler.KEY_READ_TIMESTAMP);
    }

    static Long lastWrite(Channel channel) {
        return (Long) channel.getAttribute(HeaderExchangeHandler.KEY_WRITE_TIMESTAMP);
    }

    static Long now() {
        return System.currentTimeMillis();
    }

    private void reput(Timeout timeout, Long tick) {
        if (timeout == null || tick == null) {
            throw new IllegalArgumentException();
        }

        Timer timer = timeout.timer();
        if (timer.isStop() || timeout.isCancelled()) {
            return;
        }
        timer.newTimeout(timeout.task(), tick, TimeUnit.MILLISECONDS);
    }

    public void run(Timeout timeout) throws Exception {
        Collection<Channel> c = channelProvider.getChannels();
        for(Channel channel: c) {
            if(channel.isClosed()) {
                continue;
            }
            doTask(channel);
        }
        reput(timeout, tick);
    }

    protected abstract void doTask(Channel channel);

    interface ChannelProvider {
        Collection<Channel> getChannels();
    }
}
