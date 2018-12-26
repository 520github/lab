package com.sunso.lab.framework.concurrent;

/**
 * @Title:AgileRun
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/26下午8:59
 * @m444@126.com
 */
public class AgileRun extends Thread {
    private AgileQueue agileQueue;

    public AgileRun() {
        agileQueue = new AgileQueue();
        start();
    }

    public void putQueue(AgileAction agileAction) {
        agileQueue.putQueue(agileAction);
    }

    public void run() {
        while(true) {
            AgileAction agileAction = agileQueue.getQueue();
            agileAction.action();
        }
    }
}
