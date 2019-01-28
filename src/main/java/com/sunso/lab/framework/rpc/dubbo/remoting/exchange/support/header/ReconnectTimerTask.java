package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:ReconnectTimerTask
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午3:14
 * @m444@126.com
 */
public class ReconnectTimerTask extends AbstractTimerTask {

    private static final Logger logger = LoggerFactory.getLogger(ReconnectTimerTask.class);

    private final int heartbeatTimeout;

    ReconnectTimerTask(ChannelProvider channelProvider, Long heartbeatTimeoutTick, int heartbeatTimeout1) {
        super(channelProvider, heartbeatTimeoutTick);
        this.heartbeatTimeout = heartbeatTimeout1;
    }

    @Override
    protected void doTask(Channel channel) {
        try{
            Long lastRead = lastRead(channel);
            Long now = now();

            if (lastRead != null && now - lastRead > heartbeatTimeout) {
                if (channel instanceof Client) {
                    try{
                        ((Client) channel).reconnect();
                    }catch (Exception e) {
                        //do nothing
                    }
                }
                else {
                    channel.close();
                }
            }
        }catch (Throwable t) {
            logger.warn("Exception when reconnect to remote channel " + channel.getRemoteAddress(), t);
        }
    }
}
