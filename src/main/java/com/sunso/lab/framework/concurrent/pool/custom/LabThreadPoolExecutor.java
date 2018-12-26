package com.sunso.lab.framework.concurrent.pool.custom;

import com.sunso.lab.framework.rpc.ss.S;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title:LabThreadPoolExecutor
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/28下午3:37
 * @m444@126.com
 */
public class LabThreadPoolExecutor extends AbstractExecutorService {
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    private volatile boolean allowCoreThreadTimeOut;
    private static final boolean ONLY_ONE = true;

    private final ReentrantLock mainLock = new ReentrantLock();
    private final Condition termination = mainLock.newCondition();
    private final HashSet<LabWork> workers = new HashSet<LabWork>();
    private int largestPoolSize;

    private volatile int corePoolSize;
    private volatile  int maximumPoolSize;
    private volatile long keepAliveTime;
    private volatile ThreadFactory threadFactory;
    private volatile LabRejectedExecutionHandler handler;
    private final BlockingQueue<Runnable> workQueue;

    private static final LabRejectedExecutionHandler defaultHandler =
            new LabAbortPolicy();

    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }


    private static boolean isRunning(int c) {
        return c < SHUTDOWN;
    }

    private static boolean runStateAtLeast(int c, int s) {
        return c >= s;
    }

    public LabThreadPoolExecutor(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime,
                                 TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                LabDefaultThreadFactory.defaultThreadFactory(), defaultHandler);
    }

    public LabThreadPoolExecutor(int corePoolSize,
                                 int maximumPoolSize,
                                 long keepAliveTime,
                                 TimeUnit unit,
                                 BlockingQueue<Runnable> workQueue,
                                 ThreadFactory threadFactory,
                                 LabRejectedExecutionHandler handler) {
        if(corePoolSize <= 0 ||
           maximumPoolSize<=0 ||
           maximumPoolSize < corePoolSize ||
           keepAliveTime <0 ) {
            throw new IllegalArgumentException();
        }
        if(workQueue == null ||
           threadFactory == null ||
           handler == null) {
            throw new NullPointerException();
        }
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.keepAliveTime = unit.toNanos(keepAliveTime);
        this.workQueue = workQueue;
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    @Override
    public void execute(Runnable command) {
        if(command == null) {
            throw new NullPointerException();
        }
        int c = ctl.get();
        if(workerCountOf(c) < corePoolSize) {
            if(addWorker(command, true)) {
                return;
            }
            c = ctl.get();
        }

        if(isRunning(c) && workQueue.offer(command)) {
            int recheck = ctl.get();
            if(!isRunning(recheck) && remove(command)) {
                reject(command);
            }
            else if(workerCountOf(recheck) == 0) {
                addWorker(null, false);
            }
        }
        else if(!addWorker(command, false)) {
            reject(command);
        }
    }

    public boolean remove(Runnable task) {
        boolean removed = workQueue.remove(task);
        tryTerminate();
        return removed;
    }

    protected void terminated() { }

    final void tryTerminate() {
        for(; ;) {
            int c = ctl.get();
            if(isRunning(c) ||
               runStateAtLeast(c, TIDYING) ||
               (runStateOf(c) == SHUTDOWN && !workQueue.isEmpty())) {
                return;
            }
            if(workerCountOf(c) != 0) {
                interruptIdleWorkers(ONLY_ONE);
            }

            final ReentrantLock mainLock = this.mainLock;
            mainLock.lock();
            try{
                if(ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
                    try{
                        terminated();
                    }finally {
                        ctl.set(ctlOf(TERMINATED, 0));
                        termination.signalAll();
                    }
                }
            }finally {
                mainLock.unlock();
            }
        }
    }

    private void interruptIdleWorkers(boolean onlyOne) {
        final ReentrantLock mainLock = this.mainLock;
        mainLock.lock();
        try {
            for(LabWork work: workers) {
                Thread t = work.thread;
                if(!t.isInterrupted() && work.tryLock()) {
                    try{
                        t.interrupt();
                    }finally {
                        work.unlock();
                    }
                }
                if(onlyOne){
                    break;
                }
            }
        }finally {
            mainLock.unlock();
        }
    }

    final void reject(Runnable command) {
        handler.rejectedExecution(command, this);
    }

    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for(; ;) {
            int c = ctl.get();
            int rs = runStateOf(c);
            if(rs >= SHUTDOWN && !(rs == SHUTDOWN && firstTask == null && !workQueue.isEmpty())) {
                return false;
            }
            for(; ;) {
                int wc = workerCountOf(c);
                if(wc >= CAPACITY || wc >=((core)?corePoolSize:maximumPoolSize)) {
                    return false;
                }
                if(compareAndIncrementWorkerCount(c)) {
                    break retry;
                }
                c = ctl.get();
                if(runStateOf(c) != rs) {
                    continue retry;
                }
            }
        }

        boolean workerStarted = false;
        boolean workerAdded = false;
        LabWork w = null;
        try{
            w = new LabWork(firstTask);
            final Thread t = w.thread;
            if( t != null) {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try{
                    int rs  = runStateOf(ctl.get());
                    if(rs < SHUTDOWN || (rs == SHUTDOWN && firstTask == null)) {
                        if(t.isAlive()) {
                            throw new IllegalThreadStateException();
                        }
                        workers.add(w);
                        int s = workers.size();
                        if(s > largestPoolSize) {
                            largestPoolSize = s;
                        }
                        workerAdded = true;
                    }
                }finally {
                    mainLock.unlock();
                }
                if(workerAdded) {
                    t.start();
                    workerStarted = true;
                }
            }
        }finally {
            if(!workerStarted) {

            }
        }
        return workerStarted;
    }

    /**
     * Attempts to CAS-increment the workerCount field of ctl.
     */
    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }

    /**
     * Attempts to CAS-decrement the workerCount field of ctl.
     */
    private boolean compareAndDecrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect - 1);
    }

    private void decrementWorkerCount() {
        do {} while (! compareAndDecrementWorkerCount(ctl.get()));
    }

    final void runWorker(LabWork w) {
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        w.unlock();
        boolean completedAbruptly = true;
        try{
            while (task != null || (task = getTask()) != null) {
                if((runStateAtLeast(ctl.get(), STOP) || (Thread.interrupted()
                        && runStateAtLeast(ctl.get(), STOP))) && !wt.isInterrupted()) {
                    wt.interrupt();
                }
                try{
                    beforeExecute(wt, task);
                    Throwable thrown = null;
                    try{
                        task.run();
                    }catch (RuntimeException e) {
                        thrown = e; throw e;
                    } catch (Error e) {
                        thrown = e; throw e;
                    } catch (Throwable x) {
                        thrown = x; throw new Error(x);
                    }finally {
                        afterExecute(task, thrown);
                    }
                }finally {
                    task = null;
                    w.completedTasks++;
                    w.unlock();
                }
            }
            completedAbruptly = false;
        }finally {

        }
    }

    protected void beforeExecute(Thread t, Runnable r) {

    }

    protected void afterExecute(Runnable r, Throwable t) {

    }



    private Runnable getTask() {
        boolean timeOut = false;
        for(; ;) {
            int c = ctl.get();
            int rs = runStateOf(c);
            if(rs >= SHUTDOWN && (rs >=STOP || workQueue.isEmpty())) {
                decrementWorkerCount();
                return null;
            }

            int wc = workerCountOf(c);

            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;

            if((wc > maximumPoolSize || (timed && timeOut))
                    && (wc > 1 || workQueue.isEmpty())) {
                if(compareAndDecrementWorkerCount(c)) {
                    return null;
                }
                continue;
            }

            try{
                Runnable r = timed? workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS):workQueue.take();
                if(r != null) {
                    return r;
                }
                timeOut = true;
            }catch (Exception e) {
                timeOut = false;
            }
        }
    }

    private final class LabWork extends AbstractQueuedSynchronizer implements Runnable {

        /** Thread this worker is running in.  Null if factory fails. */
        final Thread thread;
        /** Initial task to run.  Possibly null. */
        Runnable firstTask;
        /** Per-thread task counter */
        volatile long completedTasks;

        public LabWork(Runnable firstTask) {
            setState(-1);
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        @Override
        public void run() {

        }

        public void lock() {
            acquire(1);
        }

        public void unlock() {
            release(1);
        }

        public boolean tryLock()  { return tryAcquire(1); }
    }
}
