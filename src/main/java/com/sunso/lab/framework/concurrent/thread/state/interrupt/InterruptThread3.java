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
public class InterruptThread3 extends Thread {

    @Override
    public void run() {
        try {
            LockSupport.park();
            System.out.println("park ok..." + getState() + "," + getName() + "," + isInterrupted() + "," );
        } catch (Exception e) {
            System.out.println("park InterruptedException...." + getState() + "," + getName() + "," + isInterrupted() + "," + interrupted());
            e.printStackTrace();
        }
    }
}
