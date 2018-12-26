package com.sunso.lab.framework.clazz.loader;

/**
 * @Title:HotDeployMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午9:52
 * @m444@126.com
 */
public class HotDeployMain {
    public static void main(String[] args) {
        Thread thread = new Thread(new MonitorHotDeploy());
        thread.start();
    }
}
