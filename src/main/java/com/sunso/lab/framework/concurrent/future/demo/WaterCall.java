package com.sunso.lab.framework.concurrent.future.demo;

import java.util.concurrent.Callable;

/**
 * @Title:WaterCall
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午4:41
 * @m444@126.com
 */
public class WaterCall implements Callable<Water> {
    @Override
    public Water call() throws Exception {
        Thread.sleep(2000);
        System.out.println("water doing");
        return new Water();
    }
}
