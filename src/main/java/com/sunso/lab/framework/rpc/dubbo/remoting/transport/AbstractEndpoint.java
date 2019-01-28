package com.sunso.lab.framework.rpc.dubbo.remoting.transport;

import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.Codec2;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:AbstractEndpoint
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午1:50
 * @m444@126.com
 */
public abstract class AbstractEndpoint extends AbstractPeer {

    private Codec2 codec;

    private int timeout;
    private int connectTimeout;

    public AbstractEndpoint(URL url, ChannelHandler channelHandler) {
        super(url, channelHandler);
    }

    protected Codec2 getCodec() {
        return codec;
    }

}
