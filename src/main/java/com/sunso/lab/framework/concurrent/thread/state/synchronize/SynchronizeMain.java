package com.sunso.lab.framework.concurrent.thread.state.synchronize;

import java.util.concurrent.CountDownLatch;

/**
 * @Title:SynchronizeMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/2上午9:56
 * @m444@126.com
 */
public class SynchronizeMain {


    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for(int i=0; i<10; i++) {
            Thread thread = new Thread(new SynchronizeThread(countDownLatch));
            thread.start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("count-->" + SynchronizeThread.getCount());
    }
}
