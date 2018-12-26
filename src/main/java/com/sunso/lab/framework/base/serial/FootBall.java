package com.sunso.lab.framework.base.serial;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * @Title:FootBall
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午7:27
 * @m444@126.com
 */
public class FootBall extends Ball implements Externalizable {

    private int size;
    private String meterail;
    private transient String[] tag;

    public int getSize() {
        return size;
    }

    public FootBall setSize(int size) {
        this.size = size;
        return this;
    }

    public String getMeterail() {
        return meterail;
    }

    public FootBall setMeterail(String meterail) {
        this.meterail = meterail;
        return this;
    }

    public String[] getTag() {
        return tag;
    }

    public FootBall setTag(String[] tag) {
        this.tag = tag;
        return this;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(getSize());
        out.writeUTF(getColor());
        out.writeUTF(getMeterail());
        out.writeObject(getTag());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.size = in.readInt();
        setColor(in.readUTF());
        this.meterail = in.readUTF();
        this.tag = (String[])in.readObject();
    }

    public String toString() {
        String str = "football:size=" + size + ",meterail=" + meterail + ",color=" + getColor() ;
        String tags = "";
        for(String t: tag) {
            tags = tags + t+ ",";
        }
        str = str + ",tag=" + tags;
        return str;
    }
}
