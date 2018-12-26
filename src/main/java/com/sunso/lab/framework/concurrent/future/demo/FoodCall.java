package com.sunso.lab.framework.concurrent.future.demo;

import java.util.concurrent.Callable;

/**
 * @Title:FoodCall
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午4:39
 * @m444@126.com
 */
public class FoodCall implements Callable<Food> {
    @Override
    public Food call() throws Exception {
        Thread.sleep(3000);
        System.out.println("food doing");
        return new Food();
    }
}
