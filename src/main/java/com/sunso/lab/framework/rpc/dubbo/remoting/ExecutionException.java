package com.sunso.lab.framework.rpc.dubbo.remoting;

import java.net.InetSocketAddress;

/**
 * @Title:ExecutionException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午4:02
 * @m444@126.com
 */
public class ExecutionException extends RemotingException {

    private final Object request;

    public ExecutionException(Object request, Channel channel, String message, Throwable cause) {
        super(channel, message, cause);
        this.request = request;
    }


    public ExecutionException(Object request, Channel channel, String msg) {
        super(channel, msg);
        this.request = request;
    }

    public ExecutionException(Object request, Channel channel, Throwable cause) {
        super(channel, cause);
        this.request = request;
    }

    public ExecutionException(Object request, InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message,
                              Throwable cause) {
        super(localAddress, remoteAddress, message, cause);
        this.request = request;
    }

    public ExecutionException(Object request, InetSocketAddress localAddress, InetSocketAddress remoteAddress, String message) {
        super(localAddress, remoteAddress, message);
        this.request = request;
    }

    public ExecutionException(Object request, InetSocketAddress localAddress, InetSocketAddress remoteAddress, Throwable cause) {
        super(localAddress, remoteAddress, cause);
        this.request = request;
    }


    public Object getRequest() {
        return request;
    }
}
