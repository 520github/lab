package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.common.Version;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;

/**
 * @Title:HeartbeatTimerTask
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午3:08
 * @m444@126.com
 */
public class HeartbeatTimerTask extends AbstractTimerTask {

    private final int heartbeat;

    public HeartbeatTimerTask(ChannelProvider channelProvider, Long heartbeatTick, int heartbeat) {
        super(channelProvider, heartbeatTick);
        this.heartbeat = heartbeat;
    }

    @Override
    protected void doTask(Channel channel) {
        try{
            Long lastRead = lastRead(channel);
            Long lastWrite = lastWrite(channel);

            if ((lastRead != null && now() - lastRead > heartbeat)
                    || (lastWrite != null && now() - lastWrite > heartbeat)) {
                Request req = new Request();
                req.setVersion(Version.getProtocolVersion());
                req.setTwoWay(true);
                req.setEvent(Request.HEARTBEAT_EVENT);
                channel.send(req);
            }
        }catch (Throwable t) {
            //logger.warn("Exception when heartbeat to remote channel " + channel.getRemoteAddress(), t);
        }
    }
}
