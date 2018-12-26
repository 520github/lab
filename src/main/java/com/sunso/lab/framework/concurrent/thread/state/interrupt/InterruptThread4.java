package com.sunso.lab.framework.concurrent.thread.state.interrupt;

import java.util.concurrent.locks.LockSupport;

/**
 * @Title:InterruptThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/22下午8:13
 * @m444@126.com
 */
public class InterruptThread4 extends Thread {

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                //interrupted()
                //isInterrupted() 中断状态会保留
                Thread.sleep(100);
                System.out.println("cycle ok..." + getState() + "," + getName() + "," + isInterrupted() + "," );
            }
            System.out.println("cycle interrupted..." + getState() + "," + getName() + "," + isInterrupted() + "," );
        } catch (Exception e) {
            System.out.println("cycle InterruptedException...." + getState() + "," + getName() + "," + isInterrupted() + "," + interrupted());
            e.printStackTrace();
        }
    }
}
