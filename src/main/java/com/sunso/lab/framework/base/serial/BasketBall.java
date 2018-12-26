package com.sunso.lab.framework.base.serial;

import java.io.*;

/**
 * @Title:BasketBall
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午3:39
 * @m444@126.com
 */
public class BasketBall extends Ball implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String colorFlag = "color-->";
    private static final String meterailFlag = "meterail-->";
    private int num;
    private int size;
    private String meterail;
    private transient String[] tag;

    public int getSize() {
        return size;
    }

    public BasketBall setSize(int size) {
        this.size = size;
        return this;
    }

    public String getMeterail() {
        return meterail;
    }

    public BasketBall setMeterail(String meterail) {
        this.meterail = meterail;
        return this;
    }

    public String[] getTag() {
        return tag;
    }

    public BasketBall setTag(String[] tag) {
        this.tag = tag;
        return this;
    }

    private void writeObject(ObjectOutputStream o) throws IOException {
        System.out.println("writeObject");
        o.defaultWriteObject();
        o.writeInt(tag.length);
        for(int i=0; i<tag.length; i++) {
            o.writeUTF(tag[i]);
        }
    }

    private void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        o.defaultReadObject();
        int length = o.readInt();
        System.out.println("readObject length-->" + length);
        tag = new String[length];
        for(int i=0; i<length; i++) {
            tag[i] = o.readUTF();
        }
    }

    private Object writeReplace() throws ObjectStreamException {
        try{
            System.out.println("writeReplace");
            BasketBall basketBall = new BasketBall();
            basketBall.setSize(getSize()<<2);
            basketBall.setColor(colorFlag + getColor());
            basketBall.setMeterail(meterailFlag + getMeterail());
            basketBall.setTag(getTag());
            return basketBall;
        }catch (Exception e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

    private Object readResolve() throws InvalidObjectException {
        try {
            System.out.println("readResolve");
            BasketBall basketBall = new BasketBall();
            basketBall.setSize(getSize() >>2);
            basketBall.setColor(getColor().substring(colorFlag.length()));
            basketBall.setMeterail(getMeterail().substring(meterailFlag.length()));
            basketBall.setTag(getTag());
            return basketBall;
        }catch (Exception e) {
            throw new InvalidObjectException(e.getMessage());
        }
    }

    public String toString() {
        return "size=" + size + ",meterail=" + meterail + ",color=" + getColor() + ",tag="+ tag;
    }
}
