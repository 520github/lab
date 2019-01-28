package com.sunso.lab.framework.rpc.dubbo.remoting.transport.codec;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.Cleanable;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.buffer.ChannelBuffer;
import com.sunso.lab.framework.rpc.dubbo.remoting.buffer.ChannelBufferInputStream;
import com.sunso.lab.framework.rpc.dubbo.remoting.buffer.ChannelBufferOutputStream;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.AbstractCodec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:TransportCodec
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午6:38
 * @m444@126.com
 */
public class TransportCodec extends AbstractCodec {

    @Override
    public void encode(Channel channel, ChannelBuffer buffer, Object message) throws IOException {
        OutputStream outut = new ChannelBufferOutputStream(buffer);
        ObjectOutput objectOutput = getSerialization(channel).serialize(channel.getUrl(), outut);
        encodeData(channel, objectOutput, message);
        objectOutput.flushBuffer();
        if (objectOutput instanceof Cleanable) {
            ((Cleanable) objectOutput).cleanup();
        }
    }

    @Override
    public Object decode(Channel channel, ChannelBuffer buffer) throws IOException  {
        InputStream input = new ChannelBufferInputStream(buffer);
        ObjectInput objectInput = getSerialization(channel).deserialize(channel.getUrl(), input);
        Object object = decodeData(channel, objectInput);
        if (objectInput instanceof Cleanable) {
            ((Cleanable) objectInput).cleanup();
        }
        return object;
    }

    protected void encodeData(Channel channel, ObjectOutput output, Object message) throws IOException {
        encodeData(output, message);
    }

    protected void encodeData(ObjectOutput output, Object message) throws IOException {
        output.writeObject(message);
    }

    protected Object decodeData(Channel channel, ObjectInput input) throws IOException {
        return decodeData(input);
    }

    protected Object decodeData(ObjectInput input) throws IOException {
        try {
            return input.readObject();
        }catch (ClassNotFoundException e) {
            throw new IOException("ClassNotFoundException: " + StringUtils.toString(e));
        }
    }
}
