package com.sunso.lab.framework.concurrent.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Title:UnSafeDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午5:50
 * @m444@126.com
 */
public class UnSafeDemo {

    private int state;

    public UnSafeDemo() {
        this.state = 0;
    }

    public void start() {
        if(this.state !=0 || !UNSAFE.compareAndSwapInt(this, stateOffset, 0, 1)){
            return;
        }
        System.out.println("state-->" + state);
    }

    private static Unsafe getUnsafe() {
        Unsafe unsafe = null;
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return unsafe;
    }

    private static final sun.misc.Unsafe UNSAFE;
    private static final long stateOffset;
    static {
        try {
            // UNSAFE = sun.misc.Unsafe.getUnsafe();
            UNSAFE = getUnsafe();
            Class<?> usd = UnSafeDemo.class;
            stateOffset = UNSAFE.objectFieldOffset(usd.getDeclaredField("state"));
            System.out.println("stateOffset-->" + stateOffset);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        new UnSafeDemo().start();
    }

}
