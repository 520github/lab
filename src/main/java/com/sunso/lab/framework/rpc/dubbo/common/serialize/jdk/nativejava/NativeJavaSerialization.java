package com.sunso.lab.framework.rpc.dubbo.common.serialize.jdk.nativejava;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:NativeJavaSerialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午4:49
 * @m444@126.com
 */
public class NativeJavaSerialization implements Serialization {
    public static final String NAME = "nativejava";

    @Override
    public byte getContentTypeId() {
        return 7;
    }

    @Override
    public String getContentType() {
        return "x-application/nativejava";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new NativeJavaObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new NativeJavaObjectInput(inputStream);
    }
}
