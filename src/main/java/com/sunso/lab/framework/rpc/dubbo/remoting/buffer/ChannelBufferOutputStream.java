package com.sunso.lab.framework.rpc.dubbo.remoting.buffer;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @Title:ChannelBufferOutputStream
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午6:50
 * @m444@126.com
 */
public class ChannelBufferOutputStream extends OutputStream {
    private final ChannelBuffer buffer;
    private final int startIndex;

    public ChannelBufferOutputStream(ChannelBuffer buffer) {
        if (buffer == null) {
            throw new NullPointerException("buffer");
        }
        this.buffer = buffer;
        startIndex = buffer.writerIndex();
    }

    public int writtenBytes() {
        return buffer.writerIndex() - startIndex;
    }

    public void write(byte[] b, int off, int len) throws IOException {
        if (len == 0) {
            return;
        }
        buffer.writeBytes(b, off, len);
    }

    public void write(byte[] b) throws IOException {
        buffer.writeBytes(b);
    }

    @Override
    public void write(int b) throws IOException {
        buffer.writeByte((byte) b);
    }

    public ChannelBuffer buffer() {
        return buffer;
    }
}
