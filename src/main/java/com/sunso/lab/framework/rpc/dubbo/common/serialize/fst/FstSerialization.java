package com.sunso.lab.framework.rpc.dubbo.common.serialize.fst;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:FstSerialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午1:27
 * @m444@126.com
 */
public class FstSerialization implements Serialization {
    @Override
    public byte getContentTypeId() {
        return 9;
    }

    @Override
    public String getContentType() {
        return "x-application/fst";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new FstObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new FstObjectInput(inputStream);
    }
}
