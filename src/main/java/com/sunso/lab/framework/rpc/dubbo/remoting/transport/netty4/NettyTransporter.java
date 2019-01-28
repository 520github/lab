package com.sunso.lab.framework.rpc.dubbo.remoting.transport.netty4;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.Server;
import com.sunso.lab.framework.rpc.dubbo.remoting.Transporter;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:NettyTransporter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1上午11:58
 * @m444@126.com
 */
public class NettyTransporter implements Transporter {



    @Override
    public Server bind(URL url, ChannelHandler handler) {
        return new NettyServer(url, handler);
    }




}
