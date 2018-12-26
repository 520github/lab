package com.sunso.lab.framework.ds.demo.queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Title:ArrayBlockingQueueDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/16下午9:37
 * @m444@126.com
 */
public class ConcurrentLinkedQueueDemoMain {


    public static void main(String[] args) {
        Queue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        for(int i=0; i<3; i++) {
            concurrentLinkedQueue.add("add"+String.valueOf(i));
        }
        for(int i=0; i<3; i++) {
            concurrentLinkedQueue.offer("offer" + String.valueOf(i));
        }

        for(int i=0; i<3; i++) {
            String value = concurrentLinkedQueue.poll();
            System.out.println("value-->" + value);
        }

    }
}
