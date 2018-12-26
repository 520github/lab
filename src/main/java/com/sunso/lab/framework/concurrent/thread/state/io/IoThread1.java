package com.sunso.lab.framework.concurrent.thread.state.io;

/**
 * @Title:SleepThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:57
 * @m444@126.com
 */
public class IoThread1 extends Thread {

    private Thread ioThread2;

    public void setIoThread2(Thread ioThread2) {
        this.ioThread2 = ioThread2;
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("io thread1 的状态： " + getState());
                System.out.println("io thread2 的状态： " + ioThread2.getState());
                System.out.println();


                Thread.sleep(5000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
