package com.sunso.lab.framework.base.clone.deep;

import com.sunso.lab.framework.base.clone.easy.EasyFood;

/**
 * @Title:Cook
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:22
 * @m444@126.com
 */
public class DeepCook implements Cloneable {
    private int time;
    private DeepFood food;

    public int getTime() {
        return time;
    }

    public DeepCook setTime(int time) {
        this.time = time;
        return this;
    }

    public DeepFood getFood() {
        return food;
    }

    public DeepCook setFood(DeepFood food) {
        this.food = food;
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        DeepCook cook = (DeepCook) super.clone();

        DeepFood food = (DeepFood) getFood().clone();
        cook.setFood(food);

        return cook;
    }

    public String toString() {
        return "time=" + getTime() + ", food=" + food.toString();
    }
}
