package com.sunso.lab.framework.concurrent.demo;

/**
 * @Title:DemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/26下午9:51
 * @m444@126.com
 */
public class DemoMain {

    public static void main(String[] args) {
        DemoService demoService = new DemoServiceProxy();
        for(int i=0; i<100;i++) {
            demoService.say();
        }
    }
}
