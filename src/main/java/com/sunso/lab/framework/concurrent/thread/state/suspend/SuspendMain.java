package com.sunso.lab.framework.concurrent.thread.state.suspend;

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
public class SuspendMain {

    public static void main(String[] args) {
        Thread suspendThread1 = new SuspendThread1();
        Thread suspendThread2 = new SuspendThread2();
        ((SuspendThread1) suspendThread1).setSuspendThread2(suspendThread2);
        suspendThread1.start();
        suspendThread2.start();
    }
}
