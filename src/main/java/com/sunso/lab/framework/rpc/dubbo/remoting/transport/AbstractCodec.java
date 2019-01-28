package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;
import com.sunso.lab.framework.rpc.dubbo.common.util.NetUtils;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.Codec2;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @Title:AbstractCodec
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午9:26
 * @m444@126.com
 */
public abstract class AbstractCodec implements Codec2 {

    protected static void checkPayload(Channel channel, long size) throws IOException {
        int payload = Constants.DEFAULT_PAYLOAD;
        if(channel != null && channel.getUrl() != null) {
            payload = channel.getUrl().getParameter(Constants.PAYLOAD_KEY, Constants.DEFAULT_PAYLOAD);
        }
        if(payload > 0 && size > payload) {
            ExceedPayloadLimitException e = new ExceedPayloadLimitException("Data length too large: " + size + ", max payload: " + payload + ", channel: " + channel);
            throw e;
        }
    }

    protected Serialization getSerialization(Channel channel) {
        return CodecSupport.getSerialization(channel.getUrl());
    }


    protected boolean isClientSide(Channel channel) {
        String side = (String)channel.getAttribute(Constants.SIDE_KEY);
        if("client".equals(side)) {
            return true;
        }
        else if("server".equals(side)) {
            return false;
        }
        else {
            InetSocketAddress address = channel.getRemoteAddress();
            URL url = channel.getUrl();
            boolean client = url.getPort() == address.getPort()
                    && NetUtils.filterLocalHost(url.getIp()).equals(
                    NetUtils.filterLocalHost(address.getAddress()
                            .getHostAddress()));
            channel.setAttribute(Constants.SIDE_KEY, client ? "client"
                    : "server");
            return client;
        }
    }

    protected boolean isServerSide(Channel channel) {
        return !isClientSide(channel);
    }
}
