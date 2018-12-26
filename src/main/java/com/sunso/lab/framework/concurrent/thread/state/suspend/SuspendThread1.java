package com.sunso.lab.framework.concurrent.thread.state.suspend;

/**
 * @Title:SleepThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:57
 * @m444@126.com
 */
public class SuspendThread1 extends Thread {

    private Thread suspendThread2;

    public void setSuspendThread2(Thread suspendThread2) {
        this.suspendThread2 = suspendThread2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++){
            try {
                System.out.println("suspend thread1 的状态： " + getState());
                System.out.println("suspend thread2 的状态： " + suspendThread2.getState());
                System.out.println();

                if(i == 20) {
                    suspendThread2.resume();
                }

                Thread.sleep(1000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
