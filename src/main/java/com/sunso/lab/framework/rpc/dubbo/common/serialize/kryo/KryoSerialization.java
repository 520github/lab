package com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:KryoSerialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午3:24
 * @m444@126.com
 */
public class KryoSerialization implements Serialization {
    @Override
    public byte getContentTypeId() {
        return 8;
    }

    @Override
    public String getContentType() {
        return "x-application/kryo";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new KryoObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new KryoObjectInput(inputStream);
    }
}
