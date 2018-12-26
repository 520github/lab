package com.sunso.lab.framework.base.clone.deep;


/**
 * @Title:DeepCloneDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:24
 * @m444@126.com
 */
public class DeepCloneDemoMain {
    public static void main(String[] args) throws CloneNotSupportedException {
        cookTest();
    }

    private static void cookTest() throws CloneNotSupportedException {
        DeepFood DeepFood = new DeepFood();
        DeepFood.setMeat("牛");
        DeepFood.setRice("红");

        DeepCook DeepCook = new DeepCook();
        DeepCook.setTime(10);
        DeepCook.setFood(DeepFood);

        System.out.println(DeepCook.toString());

        DeepCook clone = (DeepCook) DeepCook.clone();
        clone.setTime(30);
        System.out.println(clone.toString());
        System.out.println(DeepCook.toString());

        DeepFood.setRice("黑");
        System.out.println(clone.toString());
        System.out.println(DeepCook.toString());
    }
}
