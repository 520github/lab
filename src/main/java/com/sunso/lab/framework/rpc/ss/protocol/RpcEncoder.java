package com.sunso.lab.framework.rpc.ss.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Title:RpcEncoder
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午9:29
 * @m444@126.com
 */
public class RpcEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if(!genericClass.isInstance(o)) {
            return;
        }
        byte[] data = ProtostuffSerialization.serialize(o);
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
