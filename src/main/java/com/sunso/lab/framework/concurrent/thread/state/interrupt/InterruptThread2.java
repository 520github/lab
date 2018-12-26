package com.sunso.lab.framework.concurrent.thread.state.interrupt;

/**
 * @Title:InterruptThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/22下午8:13
 * @m444@126.com
 */
public class InterruptThread2 extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(6000);
            System.out.println("ok...");
        } catch (InterruptedException e) {
            System.out.println("sleep InterruptedException...." + getState() + "," + getName() + "," + isInterrupted() + "," + interrupted());
            e.printStackTrace();
        }
    }
}
