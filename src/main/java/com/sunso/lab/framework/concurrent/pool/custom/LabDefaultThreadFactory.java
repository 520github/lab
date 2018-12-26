package com.sunso.lab.framework.concurrent.pool.custom;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title:LabDefaultThreadFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/28下午3:50
 * @m444@126.com
 */
public class LabDefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger poolNum = new AtomicInteger(1);
    private final AtomicInteger threadNum = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;

    public LabDefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        group = (s != null)? s.getThreadGroup(): Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" + poolNum.getAndIncrement() + "-thread-";
    }

    public static ThreadFactory defaultThreadFactory() {
        return new LabDefaultThreadFactory();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r, namePrefix + threadNum.getAndIncrement(), 0);
        if(thread.isDaemon()) {
            thread.setDaemon(false);
        }
        if(thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
