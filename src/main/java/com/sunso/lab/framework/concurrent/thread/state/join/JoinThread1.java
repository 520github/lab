package com.sunso.lab.framework.concurrent.thread.state.join;

/**
 * @Title:SleepThread1
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:57
 * @m444@126.com
 */
public class JoinThread1 extends Thread {

    private Thread joinThread2;
    private Thread joinThread3;

    public void setJoinThread(Thread joinThread2, Thread joinThread3) {
        this.joinThread2 = joinThread2;
        this.joinThread3 = joinThread3;
    }

    @Override
    public void run() {
        while (true){
            try {
                if(joinThread3.getState().equals("RUNNABLE")) {
                    System.out.println("join thread1 的状态： " + getState() + "," + isAlive());
                    System.out.println("join thread2 的状态： " + joinThread2.getState() + "," + joinThread2.isAlive());
                    System.out.println("join thread3 的状态： " + joinThread3.getState() + "," + joinThread3.isAlive());
                    System.out.println();
                }
                Thread.sleep(30);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
