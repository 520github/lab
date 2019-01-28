package com.sunso.lab.framework.rpc.dubbo.common.serialize.jdk.nativejava;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @Title:NativeJavaObjectOutput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午4:45
 * @m444@126.com
 */
public class NativeJavaObjectOutput implements ObjectOutput {

    private final ObjectOutputStream outputStream;

    public NativeJavaObjectOutput(OutputStream os) throws IOException {
        this(new ObjectOutputStream(os));
    }

    protected NativeJavaObjectOutput(ObjectOutputStream out) {
        Assert.notNull(out, "output == null");
        this.outputStream = out;
    }

    protected ObjectOutputStream getObjectOutputStream() {
        return outputStream;
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        outputStream.writeObject(obj);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        outputStream.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        outputStream.writeByte(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        outputStream.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        outputStream.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        outputStream.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        outputStream.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        outputStream.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        outputStream.writeUTF(v);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        if (v == null) {
            outputStream.writeInt(-1);
        } else {
            writeBytes(v, 0, v.length);
        }
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        if (v == null) {
            outputStream.writeInt(-1);
        } else {
            outputStream.writeInt(len);
            outputStream.write(v, off, len);
        }
    }

    @Override
    public void flushBuffer() throws IOException {
        outputStream.flush();
    }
}
