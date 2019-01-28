package com.sunso.lab.framework.rpc.dubbo.common.serialize.hessian2;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:Hessian2Serialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午1:56
 * @m444@126.com
 */
public class Hessian2Serialization implements Serialization {
    public static final byte ID = 2;

    @Override
    public byte getContentTypeId() {
        return ID;
    }

    @Override
    public String getContentType() {
        return "x-application/hessian2";
    }

    @Override
    public ObjectOutput serialize(URL url, OutputStream output) throws IOException {
        return new Hessian2ObjectOutput(output);
    }

    @Override
    public ObjectInput deserialize(URL url, InputStream inputStream) throws IOException {
        return new Hessian2ObjectInput(inputStream);
    }
}
