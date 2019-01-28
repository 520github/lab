package com.sunso.lab.framework.rpc.dubbo.common.util;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title:NamedThreadFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午2:43
 * @m444@126.com
 */
public class NamedThreadFactory implements ThreadFactory {
    protected static final AtomicInteger POOL_SEQ = new AtomicInteger(1);
    protected final AtomicInteger mThreadNum = new AtomicInteger(1);
    protected final String mPrefix;
    protected final boolean mDaemon;
    protected final ThreadGroup mGroup;

    public NamedThreadFactory() {
        this("pool-" + POOL_SEQ.getAndIncrement(), false);
    }

    public NamedThreadFactory(String prefix) {
        this(prefix, false);
    }

    public NamedThreadFactory(String prefix, boolean daemon) {
        mPrefix = prefix + "-thread-";
        mDaemon = daemon;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null)? Thread.currentThread().getThreadGroup(): s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread thread = new Thread(mGroup, r, name, 0);
        thread.setDaemon(mDaemon);
        return thread;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }
}
