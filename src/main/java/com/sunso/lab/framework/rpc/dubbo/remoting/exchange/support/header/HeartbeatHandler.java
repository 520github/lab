package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Response;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.AbstractChannelHandlerDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:HeartbeatHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午5:43
 * @m444@126.com
 */
public class HeartbeatHandler extends AbstractChannelHandlerDelegate {
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);

    public static String KEY_READ_TIMESTAMP = "READ_TIMESTAMP";

    public static String KEY_WRITE_TIMESTAMP = "WRITE_TIMESTAMP";

    public HeartbeatHandler(ChannelHandler handler) {
        super(handler);
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        setReadTimestamp(channel);
        setWriteTimestamp(channel);
        handler.connected(channel);
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        clearReadTimestamp(channel);
        clearWriteTimestamp(channel);
        handler.disconnected(channel);
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        setWriteTimestamp(channel);
        handler.sent(channel, message);
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        setReadTimestamp(channel);
        if(isHeartbeatRequest(message)) {
            handleHeartbeatRequest(channel, message);
            return;
        }
        else if(isHeartbeatResponse(message)) {
            handleHeartbeatResponse(channel, message);
            return;
        }
        handler.received(channel, message);
    }

    private void handleHeartbeatRequest(Channel channel, Object message) {
        Request req = (Request) message;
        if(!req.isTwoWay()) {
            return;
        }
        Response response = new Response(req.getId(), req.getVersion());
        response.setEvent(Response.HEARTBEAT_EVENT);
        channel.send(response);

    }

    private void handleHeartbeatResponse(Channel channel, Object message) {
        logger.debug("Receive heartbeat response in thread " + Thread.currentThread().getName());
    }

    private void setReadTimestamp(Channel channel) {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
    }

    private void setWriteTimestamp(Channel channel) {
        channel.setAttribute(KEY_WRITE_TIMESTAMP, System.currentTimeMillis());
    }

    private void clearReadTimestamp(Channel channel) {
        channel.removeAttribute(KEY_READ_TIMESTAMP);
    }

    private void clearWriteTimestamp(Channel channel) {
        channel.removeAttribute(KEY_WRITE_TIMESTAMP);
    }

    private boolean isHeartbeatRequest(Object message) {
        return message instanceof Request && ((Request) message).isHeartbeat();
    }

    private boolean isHeartbeatResponse(Object message) {
        return message instanceof Response && ((Response) message).isHeartbeat();
    }
}
