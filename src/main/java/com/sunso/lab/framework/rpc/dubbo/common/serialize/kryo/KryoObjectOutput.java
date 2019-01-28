package com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Output;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.Cleanable;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.kryo.utils.KryoUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title:KryoObjectOutput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午2:59
 * @m444@126.com
 */
public class KryoObjectOutput implements ObjectOutput, Cleanable {

    private Output output;
    private Kryo kryo;

    public KryoObjectOutput(OutputStream outputStream) {
        output = new Output(outputStream);
        this.kryo = KryoUtils.get();
    }

    @Override
    public void cleanup() {
        KryoUtils.release(kryo);
        kryo = null;
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        kryo.writeClassAndObject(output, obj);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        output.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        output.writeByte(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        output.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        output.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        output.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        output.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        output.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        output.writeString(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        if (v == null) {
            output.writeInt(-1);
        } else {
            writeBytes(v, 0, v.length);
        }
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        if (v == null) {
            output.writeInt(-1);
        } else {
            output.writeInt(len);
            output.write(v, off, len);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        output.flush();
    }
}
