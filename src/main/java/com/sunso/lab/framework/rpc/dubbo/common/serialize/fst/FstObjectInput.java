package com.sunso.lab.framework.rpc.dubbo.common.serialize.fst;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;
import org.nustaq.serialization.FSTObjectInput;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @Title:FstObjectInput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午11:17
 * @m444@126.com
 */
public class FstObjectInput implements ObjectInput {
    private FSTObjectInput input;

    public FstObjectInput(InputStream inputStream) {
        input = FstFactory.getDefaultFactory().getObjectInput(inputStream);
    }

    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        return input.readObject();
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        try {
            return (T) input.readObject(cls);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        try {
            return (T) input.readObject(cls);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    public boolean readBool() throws IOException {
        return input.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return input.readByte();
    }

    @Override
    public short readShort() throws IOException {
        return input.readShort();
    }

    @Override
    public int readInt() throws IOException {
        return input.readInt();
    }

    @Override
    public long readLong() throws IOException {
        return input.readLong();
    }

    @Override
    public float readFloat() throws IOException {
        return input.readFloat();
    }

    @Override
    public double readDouble() throws IOException {
        return input.readDouble();
    }

    @Override
    public String readUTF() throws IOException {
        return input.readUTF();
    }

    @Override
    public byte[] readBytes() throws IOException {
        int len = input.readInt();
        if(len < 0) {
            return null;
        }
        else if(len == 0) {
            return new byte[]{};
        }
        else {
            byte[] b = new byte[len];
            input.readFully(b);
            return b;
        }
    }
}
