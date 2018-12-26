package com.sunso.lab.framework.base.clone.serial;

import java.io.Serializable;

/**
 * @Title:Food
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:21
 * @m444@126.com
 */
public class SerialFood implements Serializable {
    private static final long serialVersionUID = 1L;
    private String rice;
    private String meat;

    public String getRice() {
        return rice;
    }

    public SerialFood setRice(String rice) {
        this.rice = rice;
        return this;
    }

    public String getMeat() {
        return meat;
    }

    public SerialFood setMeat(String meat) {
        this.meat = meat;
        return this;
    }

    public String toString() {
        return "rich=" + getRice() + ",meat=" + getMeat();
    }
}
