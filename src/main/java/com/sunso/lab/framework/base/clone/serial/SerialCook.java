package com.sunso.lab.framework.base.clone.serial;


import java.io.*;

/**
 * @Title:Cook
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午8:22
 * @m444@126.com
 */
public class SerialCook implements Serializable {
    private static final long serialVersionUID = 1L;

    private int time;
    private SerialFood food;

    public int getTime() {
        return time;
    }

    public SerialCook setTime(int time) {
        this.time = time;
        return this;
    }

    public SerialFood getFood() {
        return food;
    }

    public SerialCook setFood(SerialFood food) {
        this.food = food;
        return this;
    }

    public SerialCook clone() {
        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return (SerialCook) objectInputStream.readObject();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "time=" + getTime() + ", food=" + food.toString();
    }
}
