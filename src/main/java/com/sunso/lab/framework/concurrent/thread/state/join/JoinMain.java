package com.sunso.lab.framework.concurrent.thread.state.join;

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
public class JoinMain {

    public static void main(String[] args) {
        Thread joinThread1 = new JoinThread1();
        Thread joinThread2 = new JoinThread2();
        Thread joinThread3 = new JoinThread3();

        ((JoinThread1) joinThread1).setJoinThread(joinThread2, joinThread3);

        ((JoinThread2) joinThread2).setJoinThread3(joinThread3);

        joinThread1.start();
        joinThread2.start();
    }
}
