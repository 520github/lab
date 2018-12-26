package com.sunso.lab.framework.concurrent.demo.locks.fair;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title:FairReentrantLockThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/3下午3:54
 * @m444@126.com
 */
public class FairReentrantLockThread implements Runnable{
    private static ReentrantLock lock = new ReentrantLock(true);
    private static int count = 0;

    @Override
    public void run() {
        System.out.println("thread name-->" + Thread.currentThread().getName());
        lock.lock();
        try{
            for(int i=0; i<100000; i++) {
                count++;
            }
            System.out.println("thread-->" + Thread.currentThread().getName() + ", count-->" +count);
            try {
                Thread.sleep(40000);
                lock.unlock();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }finally {
            //lock.unlock();
        }
    }

    public static void unlock() {
        lock.unlock();
    }
}
