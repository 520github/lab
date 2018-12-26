package com.sunso.lab.framework.base.clone.deep;

/**
 * @Title:Food
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:21
 * @m444@126.com
 */
public class DeepFood implements Cloneable {
    private String rice;
    private String meat;

    public String getRice() {
        return rice;
    }

    public DeepFood setRice(String rice) {
        this.rice = rice;
        return this;
    }

    public String getMeat() {
        return meat;
    }

    public DeepFood setMeat(String meat) {
        this.meat = meat;
        return this;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String toString() {
        return "rich=" + getRice() + ",meat=" + getMeat();
    }
}
