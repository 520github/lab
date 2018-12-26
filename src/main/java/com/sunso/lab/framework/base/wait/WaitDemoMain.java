package com.sunso.lab.framework.base.wait;

/**
 * @Title:WaitDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午9:59
 * @m444@126.com
 */
public class WaitDemoMain {

    public static void main(String[] args) {
        WaitDemo waitDemo = waitTest();

        Thread notify = new Thread(new NotifyDemo(waitDemo));
        notify.start();

//        try {
//            Thread.sleep(5000);
//            System.out.println("after sleep");
//            for(int i=0; i<5; i++) {
//                synchronized (waitDemo) {
//                    waitDemo.notify();
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    private static WaitDemo waitTest() {
        WaitDemo waitDemo = new WaitDemo();
        try {
            for(int i=0; i<5; i++) {
                waitDemo.setFlag(i);
                Thread thread = new Thread(waitDemo);
                thread.start();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return waitDemo;
    }
}
