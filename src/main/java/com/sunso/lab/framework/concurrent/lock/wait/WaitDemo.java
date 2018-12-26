package com.sunso.lab.framework.concurrent.lock.wait;

/**
 * @Title:WaitDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午7:24
 * @m444@126.com
 */
public class WaitDemo {
    private static final Object lock = new Object();

    public void start() {
        run();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    private void run() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0; i<10; i++) {
                    sum+=i;
                }
                try {
                    synchronized (lock) {
                        lock.wait();
                    }
                    System.out.println("done ok...." + sum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        new WaitDemo().start();
    }

}
