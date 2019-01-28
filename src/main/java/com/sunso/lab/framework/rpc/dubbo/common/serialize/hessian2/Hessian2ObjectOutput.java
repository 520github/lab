package com.sunso.lab.framework.rpc.dubbo.common.serialize.hessian2;

import com.alibaba.com.caucho.hessian.io.Hessian2Output;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title:Hessian2ObjectOutput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午1:38
 * @m444@126.com
 */
public class Hessian2ObjectOutput implements ObjectOutput {
    private final Hessian2Output mH2o;

    public Hessian2ObjectOutput(OutputStream os) {
        mH2o = new Hessian2Output(os);
        mH2o.setSerializerFactory(Hessian2SerializerFactory.SERIALIZER_FACTORY);
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        mH2o.writeObject(obj);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        mH2o.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        mH2o.writeInt(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        mH2o.writeInt(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        mH2o.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        mH2o.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        mH2o.writeDouble(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        mH2o.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        mH2o.writeString(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        mH2o.writeBytes(v);
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        mH2o.writeBytes(v, off, len);
    }

    @Override
    public void flushBuffer() throws IOException {
        mH2o.flushBuffer();
    }
}
