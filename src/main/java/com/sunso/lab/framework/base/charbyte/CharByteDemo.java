package com.sunso.lab.framework.base.charbyte;

import java.io.UnsupportedEncodingException;

/**
 * @Title:CharByteDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/21下午1:35
 * @m444@126.com
 */
public class CharByteDemo {

    public static void printCharArray(String str) {
        char[] charArray = str.toCharArray();
        for(int i=0; i<charArray.length; i++) {
            char c = charArray[i];
            short cs = (short)c;
            byte cb = (byte)c;
            int icb = (int)cb;

            String cbin = Integer.toBinaryString(c);

            String cbHex = Integer.toHexString(cb).toUpperCase();
            String csHex = Integer.toHexString(cs).toUpperCase();
            String cbBin = Integer.toBinaryString(cb);
            String csBin = Integer.toBinaryString(cs);

            StringBuilder sb = new StringBuilder();
            sb
                    .append("c:").append(c)
                    .append(",cb:").append(cb)
                    .append(",cs:").append(cs)
                    .append(",cbhex:").append(cbHex)
                    .append(",cshex:").append(csHex)
                    .append(",cBin:").append(cbin)
                    .append(",cbBin:").append(cbBin)
                    .append(",csBin:").append(csBin)
            .append(",icb:").append(icb);
            ;
            System.out.println("sb-->" + sb.toString());
        }
    }

    public static void printBytes(byte[]  bytes) {
        System.out.println("length:" + bytes.length);
        for(byte b: bytes) {
            System.out.println("10进制:" + b + ",二进制:" + Integer.toBinaryString(b));
        }
    }

    public static void printBytesHex(byte[]  bytes) {
        System.out.println("length:" + bytes.length);
        for(byte b: bytes) {
            System.out.println("16进制："+ Integer.toHexString(b)+", 10进制:" + b + ",二进制:" + Integer.toBinaryString(b));
        }
    }

    public static void printCharHex(String str) {
        for(int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            System.out.println("c:" + c + ",chex:" + Integer.toHexString(c)+ ",cbin:" + Integer.toBinaryString(c));
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "中文";
//        printBytesHex(str.getBytes("Unicode"));
//        System.out.println("----------------");
//        printCharHex(str);
//          printBytesHex(str.getBytes("utf-8"));
//          System.out.println("----------------");
//          printCharHex(new String(str.getBytes()));
        printBytesHex(str.getBytes("gbk"));
        System.out.println("----------------");
        printCharHex(new String(str.getBytes("gbk"), "gbk"));
//        System.out.println("----------------");
        //printCharArray(str);

//        printCharArray(str);
//
//        System.out.println("----------------");
//
//        String str2 = new String(str.getBytes(), "GBk");
//        printCharArray(str2);
        System.out.println("test-->" + Integer.toHexString(-121));
    }

}
