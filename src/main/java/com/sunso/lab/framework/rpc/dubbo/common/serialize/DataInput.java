package com.sunso.lab.framework.rpc.dubbo.common.serialize;

import java.io.IOException;

/**
 * @Title:DataInput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午10:34
 * @m444@126.com
 */
public interface DataInput {
    boolean readBool() throws IOException;

    byte readByte() throws IOException;

    short readShort() throws IOException;

    int readInt() throws IOException;

    long readLong() throws IOException;

    float readFloat() throws IOException;

    double readDouble() throws IOException;

    String readUTF() throws IOException;

    byte[] readBytes() throws IOException;
}
