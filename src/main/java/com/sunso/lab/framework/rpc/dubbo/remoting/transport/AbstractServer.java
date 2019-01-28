package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.Server;
import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.util.NetUtils;

import java.net.InetSocketAddress;
import java.util.Collection;

/**
 * @Title:AbstractServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午1:59
 * @m444@126.com
 */
public abstract class AbstractServer extends AbstractEndpoint implements Server {

    protected static final String SERVER_THREAD_POOL_NAME = "DubboServerHandler";

    private InetSocketAddress localAddress;
    private InetSocketAddress bindAddress;

    public AbstractServer(URL url, ChannelHandler handler) throws RemotingException {
        super(url, handler);
        localAddress = getUrl().toInetSocketAddress();
        bindAddress = getInetSocketAddress(url);
        try{
            doOpen();
        }catch (Throwable t) {
            throw new RemotingException(url.toInetSocketAddress(), null, "Failed to bind " + getClass().getSimpleName()
                    + " on " + getLocalAddress() + ", cause: " + t.getMessage(), t);
        }
    }

    private InetSocketAddress getInetSocketAddress(URL url) {
        String bindIp = getUrl().getParameter(Constants.BIND_IP_KEY, getUrl().getHost());
        int bindPort = getUrl().getParameter(Constants.BIND_PORT_KEY, getUrl().getPort());
        if (url.getParameter(Constants.ANYHOST_KEY, false) || NetUtils.isInvalidLocalHost(bindIp)) {
            bindIp = NetUtils.ANYHOST;
        }
        return new InetSocketAddress(bindIp, bindPort);
    }

    protected abstract void doOpen() throws Throwable;

    protected abstract void doClose() throws Throwable;

    public void send(Object message, boolean sent) throws RemotingException {
        Collection<Channel> channels = getChannels();
        for(Channel channel: channels) {
            if(channel.isConnected()) {
                channel.send(message, sent);
            }
        }
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public InetSocketAddress getBindAddress() {
        return bindAddress;
    }


 }
