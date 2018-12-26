package com.sunso.lab.framework.concurrent.future.timeout;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @Title:FutureTimeoutMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/4下午8:24
 * @m444@126.com
 */
public class FutureTimeoutMain {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future task = executorService.submit(new FutureTimeoutThread());

        for(int i=0;i<2;i++) {
            Thread thread = new Thread(new FutureTimeoutGet(task));
            thread.start();
        }
//        try{
//            task.get(500, TimeUnit.SECONDS);
//            System.out.println("get value....");
//        }catch (Exception e) {
//            e.printStackTrace();
//        }finally{
//            task.cancel(true);
//        }
    }

}
