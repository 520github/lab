package com.sunso.lab.framework.ds.demo.queue;

import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Title:ArrayBlockingQueueDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/16下午9:37
 * @m444@126.com
 */
public class PriorityBlockingQueueDemoMain {


    public static void main(String[] args) {
        compareTest();
        //simpleTest();
    }

    private static void simpleTest() {
        Queue<String> priorityBlockingQueue = new PriorityBlockingQueue<>(3);

        for(int i=0; i<3; i++) {
            priorityBlockingQueue.offer("offer" + String.valueOf(i));
        }

        for(int i=0; i<3; i++) {
            priorityBlockingQueue.add("add"+String.valueOf(i));
        }

        for(int i=0; i<3; i++) {
            ((PriorityBlockingQueue<String>) priorityBlockingQueue).offer("offer-timeout" + String.valueOf(i), 5, TimeUnit.SECONDS);
        }

        for(int i=0; i<3; i++) {
            ((PriorityBlockingQueue<String>) priorityBlockingQueue).put("put" + String.valueOf(i));
        }

        for(int i=0; i<3; i++) {
            String value = priorityBlockingQueue.poll();
            System.out.println("value-->" + value);
        }

        for(int i=0; i<3; i++) {
            String value = null;
            try {
                value = ((PriorityBlockingQueue<String>) priorityBlockingQueue).poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("value-->" + value);
        }

        for(int i=0; i<3; i++) {
            String value = null;
            try {
                value = ((PriorityBlockingQueue<String>) priorityBlockingQueue).take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("value-->" + value);
        }
    }

    private static void compareTest() {
        Queue<PriorityEntity> priorityBlockingQueue = new PriorityBlockingQueue<>(3, new PriorityEntityComparator());

        for(int i=3; i<6; i++) {
            priorityBlockingQueue.offer(new PriorityEntity(i, i));
        }

        for(int i=0; i<3; i++) {
            priorityBlockingQueue.add(new PriorityEntity(i, i));
        }

        toArray(priorityBlockingQueue);

        for(int i=0; i<6; i++) {
            PriorityEntity entity = priorityBlockingQueue.poll();
            System.out.println(entity.toString());
            toArray(priorityBlockingQueue);
        }
    }

    private static void toArray(Queue<PriorityEntity> priorityBlockingQueue) {
        System.out.println("----");
        System.out.println("---begin--");
        Object[] objects = priorityBlockingQueue.toArray();
        for(Object object: objects) {
            System.out.println(object.toString());
        }
        System.out.println("---end--");
        System.out.println("----");
    }
}
