package com.sunso.lab.framework.concurrent.demo.locks.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:CountDownLatchWaitThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/4下午3:44
 * @m444@126.com
 */
public class CountDownLatchWaitThread implements Runnable {

    private CountDownLatch countDownLatch;

    public CountDownLatchWaitThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            countDownLatch.await();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "--> wait...");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
