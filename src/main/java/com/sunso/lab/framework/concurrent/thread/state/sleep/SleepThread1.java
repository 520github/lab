package com.sunso.lab.framework.concurrent.thread.state.sleep;

/**
 * @Title:SleepThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:57
 * @m444@126.com
 */
public class SleepThread1 extends Thread {

    private Thread sleepThread2;

    public void setSleepThread2(Thread sleepThread2) {
        this.sleepThread2 = sleepThread2;
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("sleep thread1 的状态： " + getState());
                System.out.println("sleep thread2 的状态： " + sleepThread2.getState());
                System.out.println();
                Thread.sleep(5000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
