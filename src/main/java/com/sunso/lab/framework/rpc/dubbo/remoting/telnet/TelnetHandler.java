package com.sunso.lab.framework.rpc.dubbo.remoting.telnet;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;

/**
 * @Title:TelnetHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午3:55
 * @m444@126.com
 */
public interface TelnetHandler {
    String telnet(Channel channel, String message) throws RemotingException;
}
