package com.sunso.lab.framework.concurrent.lock.locksupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Title:LockSupportDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午7:36
 * @m444@126.com
 */
public class LockSupportDemo {
    public void start() {
        Thread thread = run();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(thread);
        System.out.println("start end...");
    }

    public Thread run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0; i<10; i++) {
                    sum+=i;
                }
                try {
                    LockSupport.park();
                    System.out.println("done ok...." + sum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return thread;
    }

    public static void main(String[] args) {
        new LockSupportDemo().start();
    }
}
