package com.sunso.lab.framework.base.common;

/**
 * @Title:RetryDemo
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/28下午4:20
 * @m444@126.com
 */
public class RetryDemo {

    public static void retry() {
        int count = 0;
        retry:
        for(; ;) {
            count = 0;
            for(; ;) {
                count++;
                System.out.println("count->" + count);
                if(count % 5 == 0) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue retry;
                }
            }
        }
    }

    public static void main(String[] args) {
        retry();
    }
}
