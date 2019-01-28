package com.sunso.lab.framework.rpc.dubbo.remoting;

import java.net.InetSocketAddress;

/**
 * @Title:TimeoutException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午11:11
 * @m444@126.com
 */
public class TimeoutException extends RemotingException {
    public static final int CLIENT_SIDE = 0;
    public static final int SERVER_SIDE = 1;

    private final int phase;

    public TimeoutException(boolean serverSide, Channel channel, String msg) {
        super(channel, msg);
        this.phase = serverSide? SERVER_SIDE: CLIENT_SIDE;
    }

    public TimeoutException(boolean serverSide, InetSocketAddress localAddress,
                            InetSocketAddress remoteAddress, String message) {
        super(localAddress, remoteAddress, message);
        this.phase = serverSide ? SERVER_SIDE : CLIENT_SIDE;
    }

    public int getPhase() {
        return phase;
    }

    public boolean isServerSide() {
        return phase == 1;
    }

    public boolean isClientSide() {
        return phase == 0;
    }
}
