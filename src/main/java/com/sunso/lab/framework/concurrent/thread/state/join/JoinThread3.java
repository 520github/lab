package com.sunso.lab.framework.concurrent.thread.state.join;

/**
 * @Title:SleepThread2
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:58
 * @m444@126.com
 */
public class JoinThread3 extends Thread {

    @Override
    public void run() {
        try{
            int i =0;
            while (true) {
                i++;
                //System.out.println(i);
                Thread.sleep(1);
                if(i>500000000) {
                    System.out.println(i);
                }
            }
            //sleep(6000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("join thread 3....");
    }
}
