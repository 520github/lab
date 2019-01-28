package com.sunso.lab.framework.rpc.dubbo.common.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import com.sunso.lab.framework.rpc.dubbo.common.serialize.ObjectInput;

import java.io.*;
import java.lang.reflect.Type;

/**
 * @Title:FastJsonObjectInput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午4:28
 * @m444@126.com
 */
public class FastJsonObjectInput implements ObjectInput {
    private final BufferedReader reader;

    public FastJsonObjectInput(InputStream in) {
        this(new InputStreamReader(in));
    }

    public FastJsonObjectInput(Reader reader) {
        this.reader = new BufferedReader(reader);
    }


    @Override
    public Object readObject() throws IOException, ClassNotFoundException {
        String json = readLine();
        return JSON.parse(json);
    }

    @Override
    public <T> T readObject(Class<T> cls) throws IOException, ClassNotFoundException {
        return read(cls);
    }

    @Override
    public <T> T readObject(Class<T> cls, Type type) throws IOException, ClassNotFoundException {
        Object value = readObject(cls);
        //return (T) PojoUtils.realize(value, cls, type);
        return null;
    }

    @Override
    public boolean readBool() throws IOException {
        return read(boolean.class);
    }

    @Override
    public byte readByte() throws IOException {
        return read(byte.class);
    }

    @Override
    public short readShort() throws IOException {
        return read(short.class);
    }

    @Override
    public int readInt() throws IOException {
        return read(int.class);
    }

    @Override
    public long readLong() throws IOException {
        return read(long.class);
    }

    @Override
    public float readFloat() throws IOException {
        return read(float.class);
    }

    @Override
    public double readDouble() throws IOException {
        return read(double.class);
    }

    @Override
    public String readUTF() throws IOException {
        return read(String.class);
    }

    @Override
    public byte[] readBytes() throws IOException {
        return new byte[0];
    }

    private String readLine() throws IOException, EOFException {
        String line = reader.readLine();
        if (line == null || line.trim().length() == 0) {
            throw new EOFException();
        }
        return line;
    }

    private <T> T read(Class<T> cls) throws IOException {
        String json = readLine();
        return JSON.parseObject(json, cls);
    }
}
