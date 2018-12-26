package com.sunso.lab.framework.ds.demo.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Title:ArrayBlockingQueueDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/16下午9:37
 * @m444@126.com
 */
public class ArrayBlockingQueueDemoMain {


    public static void main(String[] args) {
        Queue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        for(int i=0; i<3; i++) {
            arrayBlockingQueue.add("add"+String.valueOf(i));
        }
        for(int i=0; i<3; i++) {
            try {
                ((ArrayBlockingQueue<String>) arrayBlockingQueue).put("put"+String.valueOf(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<3; i++) {
            arrayBlockingQueue.offer("offer" + String.valueOf(i));
        }
        for(int i=0; i<3; i++) {
            try {
                ((ArrayBlockingQueue<String>) arrayBlockingQueue).offer("offer_timeout" + String.valueOf(i), 5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<3; i++) {
            String value = arrayBlockingQueue.poll();
            System.out.println("value-->" + value);
        }

        for(int i=0; i<3; i++) {
            String value = null;
            try {
                value = ((ArrayBlockingQueue<String>) arrayBlockingQueue).poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("value-->" + value);
        }

        for(int i=0; i<3; i++) {
            try {
                String value = ((ArrayBlockingQueue<String>) arrayBlockingQueue).take();
                System.out.println("value-->" + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
