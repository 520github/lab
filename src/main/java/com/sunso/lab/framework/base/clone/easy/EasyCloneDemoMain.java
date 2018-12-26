package com.sunso.lab.framework.base.clone.easy;

/**
 * @Title:EasyCloneDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:24
 * @m444@126.com
 */
public class EasyCloneDemoMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        cookTest();
    }

    private static void cookTest() throws CloneNotSupportedException {
        EasyFood easyFood = new EasyFood();
        easyFood.setMeat("牛");
        easyFood.setRice("红");

        EasyCook easyCook = new EasyCook();
        easyCook.setTime(10);
        easyCook.setFood(easyFood);

        System.out.println(easyCook.toString());

        EasyCook clone = (EasyCook) easyCook.clone();
        clone.setTime(30);
        System.out.println(clone.toString());
        System.out.println(easyCook.toString());

        easyFood.setRice("黑");
        System.out.println(clone.toString());
        System.out.println(easyCook.toString());
    }
}
