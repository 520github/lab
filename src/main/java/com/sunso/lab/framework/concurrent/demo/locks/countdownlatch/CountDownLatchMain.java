package com.sunso.lab.framework.concurrent.demo.locks.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:CountDownLatchMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/4下午3:45
 * @m444@126.com
 */
public class CountDownLatchMain {

    public static void main(String[] args) {
        int threads = 2;
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        for(int i=0;i<threads;i++) {
            Thread thread = new Thread(new CountDownLatchRunThread(countDownLatch));
            thread.start();
        }

        for(int i=0; i<2; i++) {
            Thread thread = new Thread(new CountDownLatchWaitThread(countDownLatch));
            thread.start();
        }
    }
}
