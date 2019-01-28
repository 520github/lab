package com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectOutput;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff.utils.WrapperUtils;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title:ProtostuffObjectOutput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午3:47
 * @m444@126.com
 */
public class ProtostuffObjectOutput implements ObjectOutput {

    private LinkedBuffer buffer = LinkedBuffer.allocate();
    private DataOutputStream dos;

    public ProtostuffObjectOutput(OutputStream outputStream) {
        dos = new DataOutputStream(outputStream);
    }

    @Override
    public void writeObject(Object obj) throws IOException {
        byte[] bytes;
        byte[] classNameBytes;
        try {
            if(WrapperUtils.needWrapper(obj)) {
                Schema<Wrapper> schema = RuntimeSchema.getSchema(Wrapper.class);
                Wrapper wrapper = new Wrapper(obj);
                bytes = ProtobufIOUtil.toByteArray(wrapper, schema, buffer);
                classNameBytes = Wrapper.class.getName().getBytes();
            }
            else {
                Schema schema = RuntimeSchema.getSchema(obj.getClass());
                bytes = ProtobufIOUtil.toByteArray(obj, schema, buffer);
                classNameBytes = obj.getClass().getName().getBytes();
            }
        }finally {
            buffer.clear();
        }

        dos.writeInt(classNameBytes.length);
        dos.writeInt(bytes.length);
        dos.write(classNameBytes);
        dos.write(bytes);
    }

    @Override
    public void writeBool(boolean v) throws IOException {
        dos.writeBoolean(v);
    }

    @Override
    public void writeByte(byte v) throws IOException {
        dos.writeByte(v);
    }

    @Override
    public void writeShort(short v) throws IOException {
        dos.writeShort(v);
    }

    @Override
    public void writeInt(int v) throws IOException {
        dos.writeInt(v);
    }

    @Override
    public void writeLong(long v) throws IOException {
        dos.writeLong(v);
    }

    @Override
    public void writeFloat(float v) throws IOException {
        dos.writeFloat(v);
    }

    @Override
    public void writeDouble(double v) throws IOException {
        dos.writeDouble(v);
    }

    @Override
    public void writeUTF(String v) throws IOException {
        byte[] bytes = v.getBytes();
        dos.writeInt(bytes.length);
        dos.write(bytes);
    }

    @Override
    public void writeBytes(byte[] v) throws IOException {
        dos.writeInt(v.length);
        dos.write(v);
    }

    @Override
    public void writeBytes(byte[] v, int off, int len) throws IOException {
        dos.writeInt(len);
        byte[] bytes = new byte[len];
        System.arraycopy(v, off, bytes, 0, len);
        dos.write(bytes);
    }

    @Override
    public void flushBuffer() throws IOException {
        dos.flush();
    }
}
