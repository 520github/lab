package com.sunso.lab.framework.concurrent.thread.state.sleep;

/**
 * @Title:SleepMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午7:05
 * @m444@126.com
 */
public class SleepMain {

    public static void main(String[] args) {
        Thread sleepThread1 = new SleepThread1();
        Thread sleepTHread2 = new SleepThread2();
        ((SleepThread1) sleepThread1).setSleepThread2(sleepTHread2);
        sleepThread1.start();
        sleepTHread2.start();
    }
}
