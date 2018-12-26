package com.sunso.lab.framework.concurrent.thread.state.sleep;

/**
 * @Title:SleepThread2
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:58
 * @m444@126.com
 */
public class SleepThread2 extends Thread {

    @Override
    public void run() {
        try{
            sleep(500000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("sleep thread 2....");
    }
}
