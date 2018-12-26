package com.sunso.lab.framework.concurrent.pool.threadpool;

import java.util.concurrent.Callable;

/**
 * @Title:ThreadPoolCallable
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午9:27
 * @m444@126.com
 */
public class ThreadPoolCallable implements Callable<String> {
    private String name;

    public ThreadPoolCallable(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        if(name.equals("sunso") || name.equals("fintech") || name.equals("diudiu")) {
            Thread.sleep(60000*20);
        }
        System.out.println("call is good-->" + this.name);
        return "good";
    }
}
