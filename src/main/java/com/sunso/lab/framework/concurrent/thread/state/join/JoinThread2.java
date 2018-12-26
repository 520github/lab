package com.sunso.lab.framework.concurrent.thread.state.join;

/**
 * @Title:SleepThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:57
 * @m444@126.com
 */
public class JoinThread2 extends Thread {

    private Thread joinThread3;

    public void setJoinThread3(Thread joinThread3) {
        this.joinThread3 = joinThread3;
    }

    @Override
    public void run() {
        joinThread3.start();
        try {
            joinThread3.join(2000);
            System.out.println("after join");
            int i = 0;
            while(true) {
               i++;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("join thread 2....");
    }
}
