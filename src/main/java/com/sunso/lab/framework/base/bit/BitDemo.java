package com.sunso.lab.framework.base.bit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title:BitDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/29下午3:26
 * @m444@126.com
 */
public class BitDemo {


    private static final int COUNT_BITS = Integer.SIZE - 3;

    // 源码 100000000 00000000 00000000 00000001
    // 反码 111111111 11111111 11111111 11111110
    // 补码 111111111 11111111 11111111 11111111
    // 移位 111000000 00000000 00000000 00000000
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // 29个1啊
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // 111000000 00000000 00000000 00000000
    // &
    // 000111111 11111111 11111111 11111111
    // 000000000 00000000 00000000 00000000

    private static final int RUNNING_R    = -1 >> COUNT_BITS;
    private static final int SHUTDOWN_R   =  0 >> COUNT_BITS;
    private static final int STOP_R       =  1 >> COUNT_BITS;
    private static final int TIDYING_R    =  2 >> 1;
    private static final int TERMINATED_R =  3 >> 1;

    private final static  AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));

    public static void main(String[] args) {
//        printBinary();
//
//        print("COUNT_BITS-->" + COUNT_BITS);
//        print("RUNNING-->" + RUNNING);
//        print("SHUTDOWN-->" + SHUTDOWN);
//        print("STOP-->" + STOP);
//        print("TIDYING-->" + TIDYING);
//        print("TERMINATED-->" + TERMINATED);
        //orTest();
        printFnum(-20);
        print("" + (5|9));
    }

    private static void printBinary() {
        print("-1-->" + Integer.toBinaryString(-1));
        print("0-->" + Integer.toBinaryString(0));
        print("1-->" + Integer.toBinaryString(1));
        print("2-->" + Integer.toBinaryString(2));
        print("3-->" + Integer.toBinaryString(3));
        print("-------------------");

        print("-1-->" + Integer.toBinaryString(RUNNING));
        print("0-->" + Integer.toBinaryString(SHUTDOWN));
        print("1-->" + Integer.toBinaryString(STOP));
        print("2-->" + Integer.toBinaryString(TIDYING));
        print("3-->" + Integer.toBinaryString(TERMINATED));
        print("-------------------");

        print("-1-->" + Integer.toBinaryString(RUNNING_R));
        print("0-->" + Integer.toBinaryString(SHUTDOWN_R));
        print("1-->" + Integer.toBinaryString(STOP_R));
        print("2-->" + Integer.toBinaryString(TIDYING_R));
        print("3-->" + Integer.toBinaryString(TERMINATED_R));
        print("-------------------");

    }

    private static int ctlOf(int rs, int wc) { return rs | wc; }

    private static int workerCountOf(int c)  { return c & CAPACITY; }

    private static void orTest() {

        printBinary(-1);
        int c = ctl.get();
        printBinary(c);
        printBinary(CAPACITY);
        int wc = workerCountOf(c);
        printBinary(wc);

        print("------------------");

        printBinary(RUNNING);
        printBinary(0);
        int result = ctlOf(RUNNING, 0);
        printBinary(result);
    }

    private static void printFnum(int f) {
        int r = f << 2;
        printBinary(f);
        printBinary(r);
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    private static void printBinary(int i) {
        print(i+"-->" + Integer.toBinaryString(i));
    }
}
