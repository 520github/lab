package com.sunso.lab.framework.concurrent.future.demo;

import java.util.concurrent.Callable;

/**
 * @Title:RichCall
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/27下午4:41
 * @m444@126.com
 */
public class RichCall implements Callable<Rich> {
    @Override
    public Rich call() throws Exception {
        Thread.sleep(6000);
        System.out.println("rich doing");
        return new Rich();
    }
}
