package com.sunso.lab.framework.concurrent.pool.custom;

/**
 * @Title:LabRejectedExecutionHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/28下午8:01
 * @m444@126.com
 */
public interface LabRejectedExecutionHandler {
    void rejectedExecution(Runnable r, LabThreadPoolExecutor executor);
}
