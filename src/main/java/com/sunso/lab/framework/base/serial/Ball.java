package com.sunso.lab.framework.base.serial;

import java.io.Serializable;

/**
 * @Title:Ball
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午3:39
 * @m444@126.com
 */
public class Ball implements Serializable {
    private static final long serialVersionUID = 1L;

    private String color;

    public String getColor() {
        return color;
    }

    public Ball setColor(String color) {
        this.color = color;
        return this;
    }
}
