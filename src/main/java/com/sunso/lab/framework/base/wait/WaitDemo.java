package com.sunso.lab.framework.base.wait;

/**
 * @Title:WaitDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午10:00
 * @m444@126.com
 */
public class WaitDemo implements Runnable {

    int flag;

    public synchronized void waitTest() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " start..." + flag);
        if(flag == 0) {
            this.wait(3000);
        }
        if(flag == 1) {
            this.wait();
        }
        if(flag == 2) {
            Thread.sleep(3100);
        }
        if(flag == 3) {
            Thread.sleep(2000);
        }
        System.out.println(Thread.currentThread().getName() + " after wait..." + flag);
    }

    @Override
    public void run() {
        try {
            waitTest();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WaitDemo setFlag(int flag) {
        this.flag = flag;
        return this;
    }
}
