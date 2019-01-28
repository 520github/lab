package com.sunso.lab.framework.rpc.dubbo.common.serialize.jdk.java;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;

/**
 * @Title:CompactedObjectOutputStream
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午5:00
 * @m444@126.com
 */
public class CompactedObjectOutputStream extends ObjectOutputStream {
    public CompactedObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

    protected void writeClassDescriptor(ObjectStreamClass desc) throws IOException {
        Class<?> clazz = desc.forClass();
        if(clazz.isPrimitive() || clazz.isArray()) {
            write(0);
            super.writeClassDescriptor(desc);
        }
        else {
            write(1);
            writeUTF(desc.getName());
        }
    }
}
