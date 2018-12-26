package com.sunso.lab.framework.ds.demo;

/**
 * @Title:D
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/10下午5:30
 * @m444@126.com
 */
public class D {
    public static void main(String[] args) {
        System.out.println(11&110001);
        System.out.println(Integer.toBinaryString(49));
        System.out.println(Integer.toBinaryString(49^(49>>>16)));
        System.out.println(Integer.toBinaryString(0x7FFFFFFF));
        int result = 49 & 0x7FFFFFFF;
        System.out.println("result-->" + result);
        System.out.println(Integer.toBinaryString(result));
        System.out.println(Integer.toOctalString(0x7FFFFFFF));

        int a = 51669;
        int b = 51671;
        int f = 54648;
        System.out.println("f-->" + Integer.toBinaryString(f));
        System.out.println("3-->" + Integer.toBinaryString(7));
        System.out.println("ff-->" + (f&7));
        System.out.println("a-->" + Integer.toBinaryString(a));
        System.out.println("b-->" + Integer.toBinaryString(b));
        System.out.println("2c-->" + Integer.toBinaryString(2));
        System.out.println("4d-->" + Integer.toBinaryString(4));
        System.out.println(a&4);
        System.out.println(b&4);
        System.out.println(a&31);
        System.out.println(b&31);
        System.out.println(1&31);
        System.out.println(33&31);
        System.out.println(65&31);
        System.out.println(97&31);
        System.out.println(129&31);
        System.out.println(161&31);
        System.out.println(193&31);
        System.out.println(225&31);
    }
}
