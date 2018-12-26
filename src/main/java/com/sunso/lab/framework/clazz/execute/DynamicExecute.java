package com.sunso.lab.framework.clazz.execute;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Title:DynamicExecute
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午6:51
 * @m444@126.com
 */
public class DynamicExecute {
    public static void executeMainMethod(Class clazz, String[] args)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        setInputArgs(args);
        PrintStream oldStream = System.out;
        ByteArrayOutputStream byteArrayOutputStream = resetPrintStream();
        Method method = clazz.getMethod("main", String[].class);
        //method.invoke(null, (Object) new String[]{});
        method.invoke(null, (Object)args);
        System.setOut(oldStream);
        System.out.println(byteArrayOutputStream.toString());
    }

    private static void setInputArgs(String args[]) {
        StringBuilder sb = new StringBuilder();
        for(String arg: args) {
            sb.append(arg).append(" ");
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new ByteArrayInputStream(sb.toString().getBytes()));
        System.setIn(bufferedInputStream);
    }

    private static ByteArrayOutputStream resetPrintStream() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        return byteArrayOutputStream;
    }
}
