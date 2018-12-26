package com.sunso.lab.framework.concurrent.thread.state.interrupt;

/**
 * @Title:InterruptThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/22下午8:13
 * @m444@126.com
 */
public class InterruptThread1 extends Thread {

    @Override
    public void run() {
        try {
            synchronized (this) {
                this.wait();
            }
            System.out.println("ok...");
            hashCode();
        } catch (InterruptedException e) {
            System.out.println("wait InterruptedException...." + getState() + "," + getName()+ "," + isInterrupted() + "," + interrupted());
            e.printStackTrace();
        }
    }
}
