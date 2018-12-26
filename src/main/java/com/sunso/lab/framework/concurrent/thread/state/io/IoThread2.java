package com.sunso.lab.framework.concurrent.thread.state.io;

import java.util.Scanner;

/**
 * @Title:SleepThread2
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/30下午6:58
 * @m444@126.com
 */
public class IoThread2 extends Thread {

    @Override
    public void run() {
        try{
            Scanner scanner = new Scanner(System.in);
            String read = scanner.nextLine();
            System.out.println("read data-->" + read);
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("io thread 2....");
    }
}
