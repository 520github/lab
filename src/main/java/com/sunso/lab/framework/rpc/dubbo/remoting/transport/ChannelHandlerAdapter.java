package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;

/**
 * @Title:ChannelHandlerAdapter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午4:00
 * @m444@126.com
 */
public class ChannelHandlerAdapter implements ChannelHandler {
    @Override
    public void connected(Channel channel) throws RemotingException {

    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {

    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {

    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {

    }
}
