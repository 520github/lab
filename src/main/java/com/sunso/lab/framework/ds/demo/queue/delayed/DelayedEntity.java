package com.sunso.lab.framework.ds.demo.queue.delayed;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Title:DelayedEntity
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/19下午10:05
 * @m444@126.com
 */
public class DelayedEntity<T> implements Delayed {
    private T t;
    private long expireTime;
    private long delayTime;

    public DelayedEntity(T t, long expireTime) {
        this.t = t;
        this.expireTime = expireTime;
        this.delayTime = TimeUnit.NANOSECONDS.convert(expireTime, TimeUnit.SECONDS) + System.nanoTime();
    }

    public T getT() {
        return t;
    }

    public long getExpireTime() {
        return expireTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.delayTime - System.nanoTime(), unit);
    }

    @Override
    public int compareTo(Delayed o) {
        if(o == null) {
            return 1;
        }
        if(o == this) {
            return 0;
        }
        DelayedEntity cp = (DelayedEntity)o;
        if(this.expireTime == cp.getExpireTime()) {
            return 0;
        }
        return this.expireTime > cp.getExpireTime()? 1: -1;
    }
}
