package com.sunso.lab.framework.concurrent.future.timeout;

/**
 * @Title:FutureTimeoutThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/4下午8:28
 * @m444@126.com
 */
public class FutureTimeoutThread implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000*60*2);
            System.out.println("future timeout thread......");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
