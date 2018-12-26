package com.sunso.lab.framework.concurrent.thread.state.io;

import com.sunso.lab.framework.concurrent.thread.state.sleep.SleepThread1;
import com.sunso.lab.framework.concurrent.thread.state.sleep.SleepThread2;

/**
 * @Title:SleepMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午7:05
 * @m444@126.com
 */
public class IoMain {

    public static void main(String[] args) {
        Thread ioThread1 = new IoThread1();
        Thread ioTHread2 = new IoThread2();
        ((IoThread1) ioThread1).setIoThread2(ioTHread2);
        ioThread1.start();
        ioTHread2.start();
    }
}
