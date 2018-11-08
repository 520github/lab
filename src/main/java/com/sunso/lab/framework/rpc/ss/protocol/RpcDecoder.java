package com.sunso.lab.framework.rpc.ss.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Title:RpcDecoder
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午9:21
 * @m444@126.com
 */
public class RpcDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes() < 4) {
            return;
        }
        byteBuf.markReaderIndex();
        int length = byteBuf.readInt();
        if(length < 0) {
            channelHandlerContext.close();
        }
        if(byteBuf.readableBytes() < length) {
            byteBuf.resetReaderIndex();
        }
        byte[] data = new byte[length];
        byteBuf.readBytes(data);
        Object obj = ProtostuffSerialization.deserialize(data, genericClass);
        list.add(obj);
    }
}
