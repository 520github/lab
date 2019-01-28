package com.sunso.lab.framework.rpc.dubbo.rpc.protocol.dubbo;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Cleanable;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.util.ReflectUtils;
import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.Codec;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.CodecSupport;
import com.sunso.lab.framework.rpc.dubbo.rpc.Decodeable;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.RpcInvocation;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.sunso.lab.framework.rpc.dubbo.rpc.protocol.dubbo.CallbackServiceCodec.decodeInvocationArgument;

/**
 * @Title:DecodeableRpcInvocation
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/6下午7:05
 * @m444@126.com
 */
public class DecodeableRpcInvocation extends RpcInvocation implements Codec, Decodeable {
    private Channel channel;
    private InputStream inputStream;
    private Request request;
    private volatile boolean hasDecoded;
    private byte serializationType;

    public DecodeableRpcInvocation(Channel channel, Request request, InputStream is, byte id) {
        Assert.notNull(channel, "channel == null");
        Assert.notNull(request, "request == null");
        Assert.notNull(is, "inputStream == null");
        this.channel = channel;
        this.request = request;
        this.inputStream = is;
        this.serializationType = id;
    }


    @Override
    public void encode(Channel channel, OutputStream output, Object message) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object decode(Channel channel, InputStream input) throws IOException {
        ObjectInput in = CodecSupport.getSerialization(channel.getUrl(), serializationType)
                .deserialize(channel.getUrl(), input);
        String dubboVersion = in.readUTF();
        request.setVersion(dubboVersion);

        setAttachment(Constants.DUBBO_VERSION_KEY, dubboVersion);
        setAttachment(Constants.PATH_KEY, in.readUTF());
        setAttachment(Constants.VERSION_KEY, in.readUTF());

        setMethodName(in.readUTF());

        try{
            Object[] args;
            Class<?>[] pts;
            String desc = in.readUTF();
            if(desc.length() == 0) {
                pts = DubboCodec.EMPTY_CLASS_ARRAY;
                args = DubboCodec.EMPTY_OBJECT_ARRAY;
            }
            else {
                pts = ReflectUtils.desc2classArray(desc);
                args = new Object[pts.length];
                for(int i=0; i <args.length; i++) {
                    try {
                        args[i] = in.readObject(pts[i]);
                    }catch (Throwable t) {
                        //log.warn("Decode argument failed: " + e.getMessage(), e);
                    }
                }
            }
            setParameterTypes(pts);

            Map<String, String> map = (Map<String, String>) in.readObject(Map.class);
            if (map != null && map.size() > 0) {
                Map<String, String> attachment = getAttachments();
                if (attachment == null) {
                    attachment = new HashMap<String, String>();
                }
                attachment.putAll(map);
                setAttachments(attachment);
            }

            //decode argument ,may be callback
            for(int i=0; i<args.length; i++) {
                args[i] = decodeInvocationArgument(channel, this, pts, i, args[i]);
            }

            setArguments(args);
        }catch (Throwable t) {
            throw new IOException(StringUtils.toString("Read invocation data failed.", t));
        }finally {
            if (in instanceof Cleanable) {
                ((Cleanable) in).cleanup();
            }
        }
        return this;
    }

    @Override
    public void decode() throws Exception {
        if (!hasDecoded && channel != null && inputStream != null) {
            try{
                decode(channel, inputStream);
            }catch (Exception e) {
                request.setBroken(true);
                request.setData(e);
            }finally {
                hasDecoded = true;
            }
        }
    }

//    @Override
//    public Class<?>[] getParameterTypes() {
//        return new Class[0];
//    }
//
//    @Override
//    public Invoker<?> getInvoker() {
//        return null;
//    }
}
