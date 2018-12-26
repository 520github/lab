package com.sunso.lab.framework.concurrent.pool.threadpool;

import java.util.concurrent.*;

/**
 * @Title:ThreadPoolDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午9:22
 * @m444@126.com
 */
public class ThreadPoolDemo {

    private ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(
            2, 3, 100, TimeUnit.SECONDS, queue);

    public Future putCallable(Callable callable) {
        Future future = executor.submit(callable);
        return future;
    }

    public static void main(String[] args) {
        ThreadPoolDemo demo = new ThreadPoolDemo();
        demo.putCallable(new ThreadPoolCallable("sunso"));
        demo.putCallable(new ThreadPoolCallable("fintech"));
        demo.putCallable(new ThreadPoolCallable("hello"));
        demo.putCallable(new ThreadPoolCallable("diudiu"));
        demo.putCallable(new ThreadPoolCallable("bye"));
        demo.executor.shutdown();
    }
}
