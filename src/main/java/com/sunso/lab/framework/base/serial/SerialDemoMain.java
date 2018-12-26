package com.sunso.lab.framework.base.serial;

import java.io.*;

/**
 * @Title:SerialDemoMain
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/21下午3:43
 * @m444@126.com
 */
public class SerialDemoMain {
    private static final String serialFilePath = "/Users/sunso520/work/temp/db/serial.bat";

    public static void main(String[] args) {
//        serializableBall();
//        unserializableBall();

        serializableFootBall();
        unserializableFootBall();
    }

    private static void unserializableBall() {
        ObjectInputStream objectInputStream = null;
        try{
            objectInputStream = new ObjectInputStream(new FileInputStream(serialFilePath));
            BasketBall basketBall = (BasketBall)objectInputStream.readObject();
            System.out.println(basketBall.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != objectInputStream) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void serializableBall() {
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(serialFilePath));
            BasketBall basketBall = new BasketBall();
            basketBall.setSize(10001);
            basketBall.setMeterail("gold");
            basketBall.setColor("red");
            basketBall.setTag(new String[]{"gold", "good", "little"});
            objectOutputStream.writeObject(basketBall);
            objectOutputStream.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private static void unserializableFootBall() {
        ObjectInputStream objectInputStream = null;
        try{
            objectInputStream = new ObjectInputStream(new FileInputStream(serialFilePath));
            FootBall footBall = (FootBall)objectInputStream.readObject();
            System.out.println(footBall.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(null != objectInputStream) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void serializableFootBall() {
        ObjectOutputStream objectOutputStream = null;
        try{
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(serialFilePath));
            FootBall footBall = new FootBall();
            footBall.setSize(10001);
            footBall.setMeterail("gold");
            footBall.setColor("red");
            footBall.setTag(new String[]{"gold", "good", "little"});
            objectOutputStream.writeObject(footBall);
            objectOutputStream.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if(null != objectOutputStream) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
