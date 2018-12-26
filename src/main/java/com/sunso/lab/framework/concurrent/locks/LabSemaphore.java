package com.sunso.lab.framework.concurrent.locks;


import java.util.concurrent.TimeUnit;

/**
 * @Title:LabSemaphore
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/6下午6:29
 * @m444@126.com
 */
public class LabSemaphore {

    private final Sync sync;

    public LabSemaphore(int permits) {
        sync = new NonfairSync(permits);
    }

    public LabSemaphore(int permits, boolean fair) {
        sync = fair ? new FairSync(permits) : new NonfairSync(permits);
    }

    public void acquire() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    public void acquireUninterruptibly() {
        sync.acquireShared(1);
    }

    public boolean tryAcquire(){
        return sync.nonfairTryAcquireShared(1) >= 0;
    }

    public boolean tryAcquire(long timeout, TimeUnit timeUnit) throws InterruptedException  {
        return sync.tryAcquireSharedNanos(1, timeUnit.toNanos(timeout));
    }

    public void release() {
        sync.releaseShared(1);
    }

    public void acquire(int permits) throws InterruptedException {
        if (permits < 0) throw new IllegalArgumentException();
        sync.acquireSharedInterruptibly(permits);
    }

    public void acquireUninterruptibly(int permits) {
        if (permits < 0) throw new IllegalArgumentException();
        sync.acquireShared(permits);
    }

    public boolean tryAcquire(int permits) {
        if (permits < 0) throw new IllegalArgumentException();
        return sync.nonfairTryAcquireShared(permits) >= 0;
    }

    public boolean tryAcquire(int permits, long timeout, TimeUnit unit)
            throws InterruptedException {
        if (permits < 0) throw new IllegalArgumentException();
        return sync.tryAcquireSharedNanos(permits, unit.toNanos(timeout));
    }

    public void release(int permits) {
        if (permits < 0) throw new IllegalArgumentException();
        sync.releaseShared(permits);
    }

    abstract static class Sync extends LabAbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;

        Sync(int permits) {
            setState(permits);
        }

        final int getPermits() {
            return getState();
        }

        final int nonfairTryAcquireShared(int acquires) {
            for(;;) {
                int available = getState();
                int remaining = acquires - acquires;
                if(remaining <0 || compareAndSetState(available, remaining)) {
                    return remaining;
                }
            }
        }

        protected final boolean tryReleaseShared(int releases) {
            for(;;) {
                int current = getState();
                int next = current + releases;
                if (next < current) // overflow
                    throw new Error("Maximum permit count exceeded");
                if(compareAndSetState(current, next)) {
                    return true;
                }
            }
        }
    }

    static final class NonfairSync extends Sync {
        private static final long serialVersionUID = 1L;

        NonfairSync(int permits) {
            super(permits);
        }

        protected int tryAcquireShared(int acquires) {
            return super.nonfairTryAcquireShared(acquires);
        }
    }

    static final class FairSync extends Sync {
        private static final long serialVersionUID = 1L;

        FairSync(int permits) {
            super(permits);
        }

        protected int tryAcquireShared(int acquires) {
            for(;;) {
                if(hasQueuedPredecessors()){
                    return -1;
                }

                int available = getState();
                int remaining = available - acquires;
                if(remaining < 0 || compareAndSetState(available, remaining)) {
                    return remaining;
                }
            }
        }


    }
}
