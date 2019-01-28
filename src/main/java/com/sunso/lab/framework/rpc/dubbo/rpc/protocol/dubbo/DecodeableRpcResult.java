package com.sunso.lab.framework.rpc.dubbo.rpc.protocol.dubbo;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.Cleanable;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.Codec;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Response;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.CodecSupport;
import com.sunso.lab.framework.rpc.dubbo.rpc.Decodeable;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invocation;
import com.sunso.lab.framework.rpc.dubbo.rpc.RpcResult;
import com.sunso.lab.framework.rpc.dubbo.rpc.support.RpcUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @Title:DecodeableRpcResult
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午10:43
 * @m444@126.com
 */
public class DecodeableRpcResult extends RpcResult implements Codec, Decodeable {
    private Channel channel;
    private InputStream inputStream;
    private Response response;
    private Invocation invocation;
    private byte serializationType;
    private volatile boolean hasDecoded;

    public DecodeableRpcResult(Channel channel, Response response, InputStream is, Invocation invocation, byte id) {
        Assert.notNull(channel, "channel == null");
        Assert.notNull(response, "response == null");
        Assert.notNull(is, "inputStream == null");
        this.channel = channel;
        this.response = response;
        this.inputStream = is;
        this.invocation = invocation;
        this.serializationType = id;
    }

    @Override
    public void decode() throws Exception {
        if(!hasDecoded && channel != null && inputStream != null) {
            try{
                decode(channel, inputStream);
            }catch (Throwable t) {
                response.setStatus(Response.CLIENT_ERROR);
                response.setErrorMessage(StringUtils.toString(t));
            }finally {
                hasDecoded = true;
            }
        }
    }

    @Override
    public void encode(Channel channel, OutputStream output, Object message) throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object decode(Channel channel, InputStream input) throws IOException {
        ObjectInput in = CodecSupport.getSerialization(channel.getUrl(), serializationType)
                .deserialize(channel.getUrl(), input);
        byte flag = in.readByte();

        switch (flag) {
            case DubboCodec.RESPONSE_NULL_VALUE:
                break;
            case DubboCodec.RESPONSE_VALUE:
                try{
                    Type[] returnType = RpcUtils.getReturnTypes(invocation);
                    Object obj = null;
                    if(returnType == null || returnType.length == 0) {
                        obj = in.readObject();
                    }
                    else if(returnType.length == 1) {
                        obj = in.readObject((Class<?>) returnType[0]);
                    }
                    else {
                        obj = in.readObject((Class<?>) returnType[0], returnType[1]);
                    }
                    setValue(obj);
                }catch (Throwable t) {
                    throw new IOException(StringUtils.toString("Read response data failed.", t));
                }
                break;
            case DubboCodec.RESPONSE_WITH_EXCEPTION:
                try{
                    Object obj = in.readObject();
                    if (obj instanceof Throwable == false) {
                        throw new IOException("Response data error, expect Throwable, but get " + obj);
                    }
                    setException((Throwable) obj);
                }catch (Throwable t) {
                    throw new IOException(StringUtils.toString("Read response data failed.", t));
                }
                break;
            case DubboCodec.RESPONSE_NULL_VALUE_WITH_ATTACHMENTS:
                try{
                    setAttachments((Map<String, String>) in.readObject(Map.class));
                }catch (Throwable t) {
                    throw new IOException(StringUtils.toString("Read response data failed.", t));
                }
                break;
            case DubboCodec.RESPONSE_VALUE_WITH_ATTACHMENTS:
                try{
                    Type[] returnType = RpcUtils.getReturnTypes(invocation);
                    Object obj = null;
                    if(returnType == null || returnType.length == 0) {
                        obj = in.readObject();
                    }
                    else if(returnType.length == 1) {
                        obj = in.readObject((Class<?>) returnType[0]);
                    }
                    else {
                        obj = in.readObject((Class<?>) returnType[0], returnType[1]);
                    }
                    setValue(obj);
                    setAttachments((Map<String, String>) in.readObject(Map.class));
                }catch (Throwable t) {
                    throw new IOException(StringUtils.toString("Read response data failed.", t));
                }
                break;
            case DubboCodec.RESPONSE_WITH_EXCEPTION_WITH_ATTACHMENTS:
                try{
                    Object obj = in.readObject();
                    if (obj instanceof Throwable == false) {
                        throw new IOException("Response data error, expect Throwable, but get " + obj);
                    }
                    setException((Throwable) obj);
                    setAttachments((Map<String, String>) in.readObject(Map.class));
                }catch (Throwable t) {
                    throw new IOException(StringUtils.toString("Read response data failed.", t));
                }
                break;
            default:
                throw new IOException("Unknown result flag, expect '0' '1' '2', get " + flag);
        }

        if (in instanceof Cleanable) {
            ((Cleanable) in).cleanup();
        }

        return this;
    }
}
