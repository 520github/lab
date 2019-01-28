package com.sunso.lab.framework.rpc.dubbo.remoting;

import com.sunso.lab.framework.rpc.dubbo.remoting.buffer.ChannelBuffer;

import java.io.IOException;

/**
 * @Title:Codec2
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1下午12:21
 * @m444@126.com
 */
public interface Codec2 {

    void encode(Channel channel, ChannelBuffer buffer, Object message) throws IOException;

    Object decode(Channel channel, ChannelBuffer buffer) throws IOException ;


    enum DecodeResult {
        NEED_MORE_INPUT, SKIP_SOME_INPUT
    }
}
