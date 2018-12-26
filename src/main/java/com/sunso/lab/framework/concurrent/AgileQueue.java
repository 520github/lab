package com.sunso.lab.framework.concurrent;

import java.util.Stack;

/**
 * @Title:AgileQueue
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/26下午6:39
 * @m444@126.com
 */
public class AgileQueue {
    private Stack<AgileAction> stack;
    private static final int QUEUE_SIZE = 100;

    public AgileQueue() {
        stack = new Stack<AgileAction>();
    }

    public synchronized void putQueue(AgileAction agileAction) {
        while(stack.size() > QUEUE_SIZE) {
            try{
                wait();
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stack.push(agileAction);
        notifyAll();
    }

    public synchronized AgileAction getQueue() {
        while (stack.empty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        AgileAction agileAction = stack.pop();
        notifyAll();
        return agileAction;
    }
}
