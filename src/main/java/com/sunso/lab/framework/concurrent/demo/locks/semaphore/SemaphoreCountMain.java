package com.sunso.lab.framework.concurrent.demo.locks.semaphore;

import java.util.concurrent.Semaphore;

/**
 * @Title:SemaphoreCountMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/6下午6:26
 * @m444@126.com
 */
public class SemaphoreCountMain {
    private static Semaphore semaphore = new Semaphore(10);

    public static void main(String[] args) throws InterruptedException {
        semaphore.acquire(5);
        semaphore.release(20);
        System.out.println(semaphore.availablePermits());
    }

}
