package com.sunso.lab.framework.base.coding;

import com.sunso.lab.common.C;

import java.io.UnsupportedEncodingException;

/**
 * @Title:Iso88591
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/21下午2:03
 * @m444@126.com
 */
public class Iso88591 {

    private static final String CHARSET_ISO8859_1  = "ISO8859-1";

    public static void byteToString(String str) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes(CHARSET_ISO8859_1);
        String result = new String(bytes, CHARSET_ISO8859_1);
        System.out.println("result-->" + result);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "hello";
        byteToString(str);
        str = "您好";
        byteToString(str);
    }
}
