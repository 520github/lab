package com.sunso.lab.framework.base.hook;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Title:LabShutdownHook
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/30下午2:55
 * @m444@126.com
 */
public class LabShutdownHook extends Thread {

    private static final LabShutdownHook labShutdownHook = new LabShutdownHook("labShutdownHook");
    private final AtomicBoolean registered = new AtomicBoolean(false);
    private final AtomicBoolean destoryed = new AtomicBoolean(false);

    private LabShutdownHook(String name) {
        super(name);
    }

    public void run() {
        System.out.println("Run shutdown hook now.");
        doDestroy();
    }

    public static LabShutdownHook getLabShutdownHook() {
        return labShutdownHook;
    }

    public void regisger() {
        if(!registered.get() && registered.compareAndSet(false, true)) {
            Runtime.getRuntime().addShutdownHook(getLabShutdownHook());
        }
    }

    public void unregister() {
        if(registered.get() && registered.compareAndSet(true, false)) {
            Runtime.getRuntime().removeShutdownHook(getLabShutdownHook());
        }
    }


    public void doDestroy() {
        if(!destoryed.compareAndSet(false, true)) {
            return;
        }
        System.out.println("do destroy....");
    }

}
