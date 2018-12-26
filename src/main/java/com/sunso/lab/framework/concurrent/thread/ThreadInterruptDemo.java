package com.sunso.lab.framework.concurrent.thread;

/**
 * @Title:T
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午3:53
 * @m444@126.com
 */
public class ThreadInterruptDemo {

    public static void interruptTest() {
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                try {
                    while (i < 1000) {
                        Thread.sleep(500);
                        System.out.println(i++);
                    }
                }catch (InterruptedException e) {
                    System.out.println("InterruptedException");
                    Thread.currentThread().interrupted();
                }catch (Exception e) {
                    System.out.println("exception");
                    e.printStackTrace();
                }
            }
        });

        t.start();
//        try{
//            Thread.sleep(2000);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
        while (true) {
            boolean result = t.isInterrupted();
            if(result) {
                System.out.println(result);
            }
            try {
                Thread.sleep(1000);
                t.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        interruptTest();
    }

}

