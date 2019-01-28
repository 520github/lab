package com.sunso.lab.framework.rpc.dubbo.remoting.telnet.support;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.telnet.TelnetHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.ChannelHandlerAdapter;

/**
 * @Title:TelnetHandlerAdapter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午3:58
 * @m444@126.com
 */
public class TelnetHandlerAdapter extends ChannelHandlerAdapter implements TelnetHandler {
    @Override
    public String telnet(Channel channel, String message) throws RemotingException {
        return null;
    }
}
