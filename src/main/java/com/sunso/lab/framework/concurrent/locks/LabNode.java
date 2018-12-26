package com.sunso.lab.framework.concurrent.locks;

/**
 * @Title:LabNode
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/2下午12:05
 * @m444@126.com
 */
public final class LabNode {
    static final LabNode SHARE = new LabNode();
    static final LabNode EXCLUSIVE = null;
    static final int CANCELLED = 1;
    static final int SIGNAL = -1;
    static final int CONDITION = -2;
    static final int PROPAGATE = -3;

    volatile int waitStatus;

    volatile LabNode prev;
    volatile LabNode next;
    volatile Thread thread;
    LabNode nextWaiter;

    final boolean isShared() {
        return nextWaiter == SHARE;
    }

    final LabNode predecessor() {
        LabNode p = prev;
        if(p == null) {
            throw new NullPointerException();
        }
        return p;
    }

    LabNode() {

    }

    LabNode(Thread thread, LabNode mode) {
        this.thread = thread;
        this.nextWaiter = mode;
    }

    LabNode(Thread thread, int waitStatus) {
        this.thread = thread;
        this.waitStatus = waitStatus;
    }



}
