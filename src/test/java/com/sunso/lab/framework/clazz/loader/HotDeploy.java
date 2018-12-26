package com.sunso.lab.framework.clazz.loader;

/**
 * @Title:HotDeploy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/25下午9:50
 * @m444@126.com
 */
public class HotDeploy {
    public void hotMethod() {
        System.out.println("version 1-->" + this.getClass().getClassLoader());
        System.out.println("version 2-->" + this.getClass().getClassLoader());
    }
}
