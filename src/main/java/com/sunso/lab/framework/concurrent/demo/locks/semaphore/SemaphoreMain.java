package com.sunso.lab.framework.concurrent.demo.locks.semaphore;

import java.util.concurrent.*;

/**
 * @Title:SemaphoreMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/6下午4:32
 * @m444@126.com
 */
public class SemaphoreMain {

    public static void main(String[] args) {
        int threadNum = 250;

        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        ExecutorService es = Executors.newFixedThreadPool(threadNum);

        for(int i=0; i< threadNum; i++) {
            Future<SemaphoreResult> future = es.submit(new SemaphoreThread());
            Thread t = new Thread(new SemaphoreShowResultThread(future, countDownLatch));
            t.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("success-->" + SemaphoreShowResultThread.getSuccessCount());
        System.out.println("fail-->" + SemaphoreShowResultThread.getFailCount());
        es.shutdown();
    }
}
