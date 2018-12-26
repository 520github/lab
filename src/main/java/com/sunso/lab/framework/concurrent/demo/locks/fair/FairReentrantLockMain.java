package com.sunso.lab.framework.concurrent.demo.locks.fair;

/**
 * @Title:FairReentrantLockMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/3下午3:57
 * @m444@126.com
 */
public class FairReentrantLockMain {

    public static void main(String[] args) {
        for(int i=0; i<3; i++) {
            Thread thread = new Thread(new FairReentrantLockThread());
            thread.start();
        }
//        try {
//            Thread.sleep(20000);
//            FairReentrantLockThread.unlock();
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
