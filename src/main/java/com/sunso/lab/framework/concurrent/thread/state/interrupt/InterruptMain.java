package com.sunso.lab.framework.concurrent.thread.state.interrupt;

/**
 * @Title:InterruptMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/22下午7:58
 * @m444@126.com
 */
public class InterruptMain {

    public static void main(String[] args) {
        interruptWait();
        interruptSleep();
        interruptPark();
        interrupteCycle();
    }

    private static void interruptWait() {
        InterruptThread1 thread1 = new InterruptThread1();
        interruptThread(thread1);
    }

    private static void interruptSleep() {
        InterruptThread2 thread2 = new InterruptThread2();
        interruptThread(thread2);
    }

    private static void interruptPark() {
        InterruptThread3 thread3 = new InterruptThread3();
        interruptThread(thread3);
    }

    private static void interrupteCycle() {
        InterruptThread4 thread4 = new InterruptThread4();
        interruptThread(thread4);
    }

    private static void interruptThread(Thread thread) {
        thread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
        try {
            Thread.sleep(3000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(thread.getState() + "," + thread.getName() + "," + thread.isInterrupted());
    }
}
