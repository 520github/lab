package com.sunso.lab.framework.base.hook;

/**
 * @Title:LabShutdownHookTest
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/30下午3:06
 * @m444@126.com
 */
public class LabShutdownHookTest {

    static {
        register();
    }

    public static void main(String[] args) {
        System.out.println("---test---");
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void register() {
        LabShutdownHook.getLabShutdownHook().regisger();
    }
}
