package com.sunso.lab.framework.concurrent.demo;

/**
 * @Title:DemoServiceImpl
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/26下午9:44
 * @m444@126.com
 */
public class DemoServiceImpl implements DemoService{
    @Override
    public void say() {
        System.out.println(Thread.currentThread().getName() + " say hello......");
    }
}
