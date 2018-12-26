package com.sunso.lab.framework.concurrent.demo.locks.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:CountDownLatchRunThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/4下午3:41
 * @m444@126.com
 */
public class CountDownLatchRunThread implements Runnable {

    private CountDownLatch countDownLatch;

    public CountDownLatchRunThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try{
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName() + "--> run...");
            countDownLatch.countDown();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
