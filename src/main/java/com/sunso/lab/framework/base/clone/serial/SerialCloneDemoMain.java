package com.sunso.lab.framework.base.clone.serial;

/**
 * @Title:SerialCloneDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:39
 * @m444@126.com
 */
public class SerialCloneDemoMain {

    public static void main(String[] args) {
        serialClone();
    }

    private static void serialClone() {
        SerialFood SerialFood = new SerialFood();
        SerialFood.setMeat("牛");
        SerialFood.setRice("红");

        SerialCook SerialCook = new SerialCook();
        SerialCook.setTime(10);
        SerialCook.setFood(SerialFood);

        System.out.println(SerialCook.toString());

        SerialCook clone = SerialCook.clone();
        clone.setTime(30);
        System.out.println(clone.toString());
        System.out.println(SerialCook.toString());

        SerialFood.setRice("黑");
        System.out.println(clone.toString());
        System.out.println(SerialCook.toString());
    }
}
