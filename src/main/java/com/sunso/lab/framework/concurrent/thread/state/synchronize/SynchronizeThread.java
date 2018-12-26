package com.sunso.lab.framework.concurrent.thread.state.synchronize;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:SynchronizeThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/2上午9:54
 * @m444@126.com
 */
public class SynchronizeThread implements Runnable {
    private CountDownLatch countDownLatch;
    private static int count = 0;

    public SynchronizeThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        synchronized (SynchronizeThread.class) {
            for(int i=0; i<1000000; i++) {
                count++;
            }
            this.countDownLatch.countDown();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getCount() {
        return count;
    }
}
