package com.sunso.lab.framework.concurrent.pool.custom;

import java.util.concurrent.RejectedExecutionException;

/**
 * @Title:LabAbortPolicy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/28下午8:02
 * @m444@126.com
 */
public class LabAbortPolicy implements LabRejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, LabThreadPoolExecutor e) {
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                e.toString());
    }
}
