package com.sunso.lab.framework.rpc.dubbo.common.timer;

/**
 * @Title:Timeout
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午11:34
 * @m444@126.com
 */
public interface Timeout {
    /**
     * Returns the {@link Timer} that created this handle.
     */
    Timer timer();

    /**
     * Returns the {@link TimerTask} which is associated with this handle.
     */
    TimerTask task();

    /**
     * Returns {@code true} if and only if the {@link TimerTask} associated
     * with this handle has been expired.
     */
    boolean isExpired();

    /**
     * Returns {@code true} if and only if the {@link TimerTask} associated
     * with this handle has been cancelled.
     */
    boolean isCancelled();

    /**
     * Attempts to cancel the {@link TimerTask} associated with this handle.
     * If the task has been executed or cancelled already, it will return with
     * no side effect.
     *
     * @return True if the cancellation completed successfully, otherwise false
     */
    boolean cancel();
}
