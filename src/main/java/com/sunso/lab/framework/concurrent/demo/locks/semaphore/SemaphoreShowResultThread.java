package com.sunso.lab.framework.concurrent.demo.locks.semaphore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title:SemaphoreShowResultThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/6下午5:17
 * @m444@126.com
 */
public class SemaphoreShowResultThread implements Runnable {
    final static AtomicInteger success = new AtomicInteger(0);
    final static AtomicInteger fail = new AtomicInteger(0);
    static CountDownLatch countDownLatch;

    Future<SemaphoreResult> future;

    public SemaphoreShowResultThread(Future<SemaphoreResult> future, CountDownLatch countDownLatch ) {
        this.future = future;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            SemaphoreResult result = future.get();
            System.out.println(result.getThreadName() + " execute return result....." + success.addAndGet(1));
        } catch (Exception e) {
            System.out.println(e.getMessage() + "....." + fail.addAndGet(1));
        }finally {
            countDownLatch.countDown();
        }
    }

    public static int getSuccessCount() {
        return success.get();
    }

    public static int getFailCount() {
        return fail.get();
    }
}
