package com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher.all;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.Dispatcher;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:AllDispatcher
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午3:34
 * @m444@126.com
 */
public class AllDispatcher implements Dispatcher {
    @Override
    public ChannelHandler dispatch(ChannelHandler handler, URL url) {
        return new AllChannelHandler(handler, url);
    }
}
