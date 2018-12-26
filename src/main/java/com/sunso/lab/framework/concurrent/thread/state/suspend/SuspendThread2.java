package com.sunso.lab.framework.concurrent.thread.state.suspend;

/**
 * @Title:SleepThread2
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:58
 * @m444@126.com
 */
public class SuspendThread2 extends Thread {

    @Override
    public void run() {
        try{
            System.out.println("suspend thread 2....");
            suspend();
            System.out.println("resume thread 2....");
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("fininsh thread 2....");
    }
}
