package com.sunso.lab.framework.concurrent.future.timeout;

import java.util.concurrent.Future;

/**
 * @Title:FutureTimeoutGet
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/5下午8:18
 * @m444@126.com
 */
public class FutureTimeoutGet implements Runnable {
    private Future futureTask;

    public FutureTimeoutGet(Future futureTask) {
        this.futureTask = futureTask;
    }

    @Override
    public void run() {
        try{
            futureTask.get();
            System.out.println(Thread.currentThread().getName() + " get ok...");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
