package com.sunso.lab.framework.rpc.dubbo.common.serialize.hessian2;

import com.alibaba.com.caucho.hessian.io.Hessian2Input;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @Title:Hessian2ObjectInput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午1:33
 * @m444@126.com
 */
public class Hessian2ObjectInput implements ObjectInput {

    private final Hessian2Input mH2i;

    public Hessian2ObjectInput(InputStream inputStream) {
        mH2i = new Hessian2Input(inputStream);
        mH2i.setSerializerFactory(Hessian2SerializerFactory.SERIALIZER_FACTORY);
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return mH2i.readObject();
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return (T) mH2i.readObject(cls);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        return readObject(cls);
    }

    @Override
    public boolean readBool() throws IOException {
        return mH2i.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return (byte) mH2i.readInt();
    }

    @Override
    public short readShort() throws IOException {
        return (short) mH2i.readInt();
    }

    @Override
    public int readInt() throws IOException {
        return mH2i.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return mH2i.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return (float) mH2i.readDouble();
    }

    @Override
    public double readDouble() throws IOException {
        return mH2i.readDouble();
    }

    @Override
    public String readUTF() throws IOException {
        return mH2i.readString();
    }

    @Override
    public byte[] readBytes() throws IOException {
        return mH2i.readBytes();
    }
}
