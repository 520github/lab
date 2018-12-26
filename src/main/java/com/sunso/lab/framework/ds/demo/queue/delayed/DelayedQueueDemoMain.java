package com.sunso.lab.framework.ds.demo.queue.delayed;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Title:DelayedQueueDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/19下午10:16
 * @m444@126.com
 */
public class DelayedQueueDemoMain {
    public static void main(String[] args) {
        //cacheTest();
        delayTest();
    }

    private static void cacheTest() {
        DelayedCache<String, Integer> cache = new DelayedCache<>();
        for(int i=1; i<11; i++) {
            cache.put(String.valueOf(i), i, i);
        }
    }

    private static void delayTest() {
        DelayQueue<DelayedEntity<String>> queue = new DelayQueue<>();
        for(int i=0; i<10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DelayedEntity<String> entity = queue.poll(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=1;i<=10;i++) {
            queue.put(new DelayedEntity<>(String.valueOf(i), i));
        }
    }
}
