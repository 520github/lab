package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;

/**
 * @Title:ChannelHandlerDelegate
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午3:40
 * @m444@126.com
 */
public interface ChannelHandlerDelegate extends ChannelHandler {
    ChannelHandler getHandler();
}
