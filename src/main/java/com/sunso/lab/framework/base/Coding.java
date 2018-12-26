package com.sunso.lab.framework.base;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Title:B
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/20下午5:21
 * @m444@126.com
 */
public class Coding {

    public static final String CHARSET_UTF8 = "utf-8";
    public static final String CHARSET_GBK = "gbk";

    public static String getDefaultCharsetName() {
        return Charset.defaultCharset().name();
    }

    public static int getStringLenth() {
        //new String("", "")
        return 0;
    }

    public static int getByteLength(String str, String charset) throws UnsupportedEncodingException {
        return str.getBytes(charset).length;
    }

    public static byte[] getBytes(String str, String charset) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(charset);
        printBytes(bytes);
        return bytes;
    }

    public static void printBytes(byte[]  bytes) {
        System.out.println("length:" + bytes.length);
        for(byte b: bytes) {

        }
    }

    public static void getAvailableCharset() {
        Set<String> charsetNames = Charset.availableCharsets().keySet();
        Iterator<String> iterator = charsetNames.iterator();
        while(iterator.hasNext()) {
            String charset = iterator.next();
            System.out.println(charset);
        }
    }

    public static void testCoding() {
        String str = "我是 cm";
        //str.toCharArray()
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String dcn = Coding.getDefaultCharsetName();
        System.out.println("dcn-->" + dcn);

//        String str = "你是什么鬼";
//
//        int length = getByteLength(str, CHARSET_UTF8);
//        System.out.println("length-->" + length);
//        length = getByteLength(str, CHARSET_GBK);
//        System.out.println("length-->" + length);
//
//        byte[] bytes = getBytes(str, CHARSET_UTF8);
//        System.out.println("bytes-->" + bytes);

        // getAvailableCharset();
    }

}
