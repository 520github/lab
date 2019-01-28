package com.sunso.lab.framework.rpc.dubbo.common.serialize.fastjson;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:FastJsonSerialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午4:34
 * @m444@126.com
 */
public class FastJsonSerialization implements Serialization {

    @Override
    public byte getContentTypeId() {
        return 6;
    }

    @Override
    public String getContentType() {
        return "text/json";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new FastJsonObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new FastJsonObjectInput(inputStream);
    }
}
