package com.sunso.lab.framework.base.clone.easy;

/**
 * @Title:Food
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:21
 * @m444@126.com
 */
public class EasyFood {
    private String rice;
    private String meat;

    public String getRice() {
        return rice;
    }

    public EasyFood setRice(String rice) {
        this.rice = rice;
        return this;
    }

    public String getMeat() {
        return meat;
    }

    public EasyFood setMeat(String meat) {
        this.meat = meat;
        return this;
    }

    public String toString() {
        return "rich=" + getRice() + ",meat=" + getMeat();
    }
}
