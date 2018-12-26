package com.sunso.lab.framework.ds.demo.queue;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
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
public class LinkedBlockingQueueDemoMain {


    public static void main(String[] args) {
        Queue<String> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        for(int i=0; i<3; i++) {
            linkedBlockingQueue.add("add"+String.valueOf(i));
        }
        for(int i=0; i<3; i++) {
            try {
                ((LinkedBlockingQueue<String>) linkedBlockingQueue).put("put"+String.valueOf(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for(int i=0; i<3; i++) {
            linkedBlockingQueue.offer("offer" + String.valueOf(i));
        }
        for(int i=0; i<3; i++) {
            try {
                ((LinkedBlockingQueue<String>) linkedBlockingQueue).offer("offer_timeout" + String.valueOf(i), 5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i<3; i++) {
            String value = linkedBlockingQueue.poll();
            System.out.println("value-->" + value);
        }

        for(int i=0; i<3; i++) {
            String value = null;
            try {
                value = ((LinkedBlockingQueue<String>) linkedBlockingQueue).poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("value-->" + value);
        }

        for(int i=0; i<3; i++) {
            try {
                String value = ((LinkedBlockingQueue<String>) linkedBlockingQueue).take();
                System.out.println("value-->" + value);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
