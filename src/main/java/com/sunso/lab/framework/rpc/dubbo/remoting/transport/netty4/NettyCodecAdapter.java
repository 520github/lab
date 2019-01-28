package com.sunso.lab.framework.rpc.dubbo.remoting.transport.netty4;

import com.sunso.lab.framework.rpc.dubbo.remoting.Codec2;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @Title:NettyCodecAdapter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1下午12:34
 * @m444@126.com
 */
public class NettyCodecAdapter {

    private final ChannelHandler encoder = new InternalEncoder();
    private final ChannelHandler decoder = new InternalDecoder();

    private final Codec2 codec;
    private final URL url;
    private final com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler handler;

    public NettyCodecAdapter(Codec2 codec, URL url, com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler handler) {
        this.codec = codec;
        this.url = url;
        this.handler = handler;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    private class InternalEncoder extends MessageToByteEncoder {
        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {

        }
    }

    private class InternalDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        }
    }
}
