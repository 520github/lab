package com.sunso.lab.framework.concurrent.demo.locks.semaphore;

/**
 * @Title:SemaphoreResult
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/6下午4:22
 * @m444@126.com
 */
public class SemaphoreResult {
    String threadName;

    public String getThreadName() {
        return Thread.currentThread().getName();
    }
}
