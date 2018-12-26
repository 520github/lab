package com.sunso.lab.framework.clazz;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @Title:LoadClass
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午3:25
 * @m444@126.com
 */
public class LabClassLoader extends ClassLoader {

    private String classPath;

    public LabClassLoader(ClassLoader parent, String classPath) {
        super(parent);
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        String classFile = classPath + className.replaceAll("\\.", "\\") + ".class";
        Class clazz = null;
        try {
            byte[] data = getClassFileByte(classFile);
            clazz = defineClass(className, data, 0 , data.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private byte[] getClassFileByte(String classFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(classFile);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        WritableByteChannel writableByteChannel = Channels.newChannel(byteArrayOutputStream);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        while(true) {
            int i = fileChannel.read(byteBuffer);
            if(i == 0 || i == -1) {
                break;
            }
            byteBuffer.flip();
            writableByteChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        fileChannel.close();
        return byteArrayOutputStream.toByteArray();
    }
}
