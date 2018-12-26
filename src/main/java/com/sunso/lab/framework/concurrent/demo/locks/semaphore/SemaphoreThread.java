package com.sunso.lab.framework.concurrent.demo.locks.semaphore;

import java.util.concurrent.*;

/**
 * @Title:SemaphoreThread
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/6下午4:05
 * @m444@126.com
 */
public class SemaphoreThread implements Callable<SemaphoreResult> {
    final static int MAX_QPS = 50;
    final static Semaphore semaphore = new Semaphore(MAX_QPS);

    static{
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                semaphore.release(MAX_QPS - semaphore.availablePermits());
            }
        }, 1000, 50, TimeUnit.MILLISECONDS);
    }

    @Override
    public SemaphoreResult call() throws Exception {
        try{
            //semaphore.acquireUninterruptibly(1);

            //只会暂时固定的时间，时间一到返回获取结果，true或false
//            semaphore.tryAcquire(1, TimeUnit.SECONDS);
//            semaphore.tryAcquire();
//
//            //获取不到线程会被暂停，需要被唤醒，有中断
//            semaphore.acquire();
            semaphore.acquire(1);
//
//            //获取不到线程会被暂停,需要被唤醒，但没有中断
//            semaphore.acquireUninterruptibly();
//            semaphore.acquireUninterruptibly(1);

            if(semaphore.tryAcquire(1, TimeUnit.SECONDS)) {
                return callRemote();
            }
            else {
                //System.out.println(Thread.currentThread().getName() + " timeout now....");
                throw new TimeoutException(Thread.currentThread().getName() + " timeout now....");
            }

        }finally {
            //semaphore.release(1);
        }
    }

    private SemaphoreResult callRemote() {
        try{
            System.out.println(Thread.currentThread().getName() + "," + getAvaliablePermits());
            Thread.sleep(3000);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new SemaphoreResult();
    }

    public int getAvaliablePermits() {
        return semaphore.availablePermits();
    }
}
