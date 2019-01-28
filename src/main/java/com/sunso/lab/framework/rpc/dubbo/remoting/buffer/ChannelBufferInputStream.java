package com.sunso.lab.framework.rpc.dubbo.remoting.buffer;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title:ChannelBufferInputStream
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午7:02
 * @m444@126.com
 */
public class ChannelBufferInputStream extends InputStream {

    private final ChannelBuffer buffer;
    private final int startIndex;
    private final int endIndex;

    public ChannelBufferInputStream(ChannelBuffer buffer) {
        this(buffer, buffer.readableBytes());
    }

    public ChannelBufferInputStream(ChannelBuffer buffer, int length) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        if (length < 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        if (length > buffer.readableBytes()) {
            throw new IndexOutOfBoundsException();
        }

        this.buffer = buffer;
        startIndex = buffer.readerIndex();
        endIndex = startIndex + length;
        buffer.markReaderIndex();
    }

    public int readBytes() {
        return buffer.readerIndex() - startIndex;
    }

    public int available() throws IOException {
        return endIndex - buffer.readerIndex();
    }

    public void mark(int readlimit) {
        buffer.markReaderIndex();
    }

    public boolean markSupported() {
        return true;
    }

    @Override
    public int read() throws IOException {
        if (!buffer.readable()) {
            return -1;
        }
        return buffer.readByte() & 0xff;
    }

    public int read(byte[] b, int off, int len) throws IOException {
        int available = available();
        if (available == 0) {
            return -1;
        }
        len = Math.min(available, len);
        buffer.readBytes(b, off, len);
        return len;
    }

    public void reset() throws IOException {
        buffer.resetReaderIndex();
    }

    public long skip(long n) throws IOException {
        if (n > Integer.MAX_VALUE) {
            return skipBytes(Integer.MAX_VALUE);
        } else {
            return skipBytes((int) n);
        }
    }

    private int skipBytes(int n) throws IOException {
        int nBytes = Math.min(available(), n);
        buffer.skipBytes(nBytes);
        return nBytes;
    }

}
