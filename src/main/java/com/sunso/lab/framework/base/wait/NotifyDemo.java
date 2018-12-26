package com.sunso.lab.framework.base.wait;

/**
 * @Title:NotifyDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/22下午5:19
 * @m444@126.com
 */
public class NotifyDemo implements Runnable {
    WaitDemo waitDemo;

    public NotifyDemo(WaitDemo waitDemo) {
        this.waitDemo = waitDemo;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(30000);
                synchronized (waitDemo) {
                    waitDemo.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
