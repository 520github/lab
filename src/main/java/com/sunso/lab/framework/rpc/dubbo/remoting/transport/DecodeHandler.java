package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Response;
import com.sunso.lab.framework.rpc.dubbo.rpc.Decodeable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Title:DecodeHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午3:56
 * @m444@126.com
 */
public class DecodeHandler extends AbstractChannelHandlerDelegate {

    private static final Logger log = LoggerFactory.getLogger(DecodeHandler.class);

    public DecodeHandler(ChannelHandler handler) {
        super(handler);
    }

    public void received(Channel channel, Object message) throws RemotingException {
        if(message instanceof Decodeable) {
            decode(message);
        }

        if(message instanceof Request) {
            decode(((Request) message).getData());
        }

        if (message instanceof Response) {
            decode(((Response) message).getResult());
        }

        handler.received(channel, message);
    }

    private void decode(Object message) {
        if(message != null && message instanceof Decodeable) {
            try{
                ((Decodeable) message).decode();
                log.debug("Decode decodeable message " + message.getClass().getName());
            }catch (Throwable e) {
                log.warn("Call Decodeable.decode failed: " + e.getMessage(), e);
            }
        }
    }
}
