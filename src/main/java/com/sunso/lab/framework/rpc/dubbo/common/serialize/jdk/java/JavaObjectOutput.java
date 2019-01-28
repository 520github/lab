package com.sunso.lab.framework.rpc.dubbo.common.serialize.jdk.java;

import com.sunso.lab.framework.rpc.dubbo.common.serialize.jdk.nativejava.NativeJavaObjectOutput;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @Title:JavaObjectOutput
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午4:55
 * @m444@126.com
 */
public class JavaObjectOutput extends NativeJavaObjectOutput {

    public JavaObjectOutput(OutputStream os) throws IOException {
        super(new ObjectOutputStream(os));
    }

    public JavaObjectOutput(OutputStream os, boolean compact) throws IOException {
        super(compact ? new CompactedObjectOutputStream(os) : new ObjectOutputStream(os));
    }

    public void writeUTF(String v) throws IOException {
        if (v == null) {
            getObjectOutputStream().writeInt(-1);
        } else {
            getObjectOutputStream().writeInt(v.length());
            getObjectOutputStream().writeUTF(v);
        }
    }

    public void writeObject(Object obj) throws IOException {
        if (obj == null) {
            getObjectOutputStream().writeByte(0);
        } else {
            getObjectOutputStream().writeByte(1);
            getObjectOutputStream().writeObject(obj);
        }
    }

    public void flushBuffer() throws IOException {
        getObjectOutputStream().flush();
    }
}
