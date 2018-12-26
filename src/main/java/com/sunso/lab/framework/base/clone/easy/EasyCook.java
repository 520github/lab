package com.sunso.lab.framework.base.clone.easy;

/**
 * @Title:Cook
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:22
 * @m444@126.com
 */
public class EasyCook implements Cloneable {
    private int time;
    private EasyFood food;

    public int getTime() {
        return time;
    }

    public EasyCook setTime(int time) {
        this.time = time;
        return this;
    }

    public EasyFood getFood() {
        return food;
    }

    public EasyCook setFood(EasyFood food) {
        this.food = food;
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "time=" + getTime() + ", food=" + food.toString();
    }
}
