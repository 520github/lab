package com.sunso.lab.framework.rpc.dubbo.remoting.buffer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @Title:ChannelBuffer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1下午12:24
 * @m444@126.com
 */
public interface ChannelBuffer extends Comparable<ChannelBuffer> {
    int capacity();

    void clear();

    ChannelBuffer copy();

    ChannelBuffer copy(int index, int length);

    void discardReadBytes();

    void ensureWritableBytes(int writableBytes);

    void markReaderIndex();

    int readableBytes();

    boolean readable();

    byte readByte();

    void readBytes(byte[] dst, int dstIndex, int length);

    @Override
    public boolean equals(Object o);

    byte getByte(int index);

    void getBytes(int index, byte[] dst);

    int setBytes(int index, InputStream src, int length) throws IOException;

    void skipBytes(int length);

    void readBytes(OutputStream dst, int length) throws IOException;

    void readerIndex(int readerIndex);

    int readerIndex();

    void resetWriterIndex();

    void resetReaderIndex();

    ChannelBuffer readBytes(int length);

    void readBytes(byte[] dst);

    void readBytes(ChannelBuffer dst, int dstIndex, int length);

    void readBytes(ChannelBuffer dst, int length);

    void setIndex(int readerIndex, int writerIndex);

    void setBytes(int index, ChannelBuffer src, int srcIndex, int length);

    void setBytes(int index, ChannelBuffer src, int length);

    void setBytes(int index, ChannelBuffer src);

    void setBytes(int index, ByteBuffer src);

    void setBytes(int index, byte[] src, int srcIndex, int length);

    void setBytes(int index, byte[] src);

    void setByte(int index, int value);

    int arrayOffset();

    boolean hasArray();

    byte[] array();

    void writerIndex(int writerIndex);

    int writerIndex();

    int writeBytes(InputStream src, int length) throws IOException;

    void writeBytes(ChannelBuffer src, int srcIndex, int length);

    void writeBytes(ChannelBuffer src, int length);

    void writeBytes(ChannelBuffer src);

    void writeBytes(ByteBuffer src);

    void writeBytes(byte[] src, int index, int length);

    void writeBytes(byte[] src);

    void writeByte(int value);

    int writableBytes();

    boolean writable();

    ByteBuffer toByteBuffer(int index, int length);

    ByteBuffer toByteBuffer();
}
