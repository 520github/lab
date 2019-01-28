package com.sunso.lab.framework.rpc.dubbo.common.serialize.jdk.java;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:CompactedJavaSerialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午5:06
 * @m444@126.com
 */
public class CompactedJavaSerialization implements Serialization {
    @Override
    public byte getContentTypeId() {
        return 4;
    }

    @Override
    public String getContentType() {
        return "x-application/compactedjava";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new JavaObjectOutput(output, true);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new JavaObjectInput(inputStream, true);
    }
}
