package com.sunso.lab.framework.socker.hello;

import java.io.*;
import java.net.Socket;

/**
 * @Title:HelloSocketClient
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/19下午9:22
 * @m444@126.com
 */
public class HelloSocketClient implements Runnable {
    Socket clientSocket = null;
    BufferedReader bufferedReader;
    DataOutputStream dataOutputStream;
    Thread thread;

    public static void main(String[] args) {
        new HelloSocketClient().startClient();
    }

    public void startClient() {
        try {
            clientSocket = new Socket("localhost", 9080);
            while(true) {
                InputStream inputStream = clientSocket.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                OutputStream outputStream = clientSocket.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);

                thread = new Thread(this);
                thread.start();

                String line;
                while ((line =bufferedReader.readLine()) != null) {
                    System.out.println("content-->" + line);
                }

                inputStream.close();
                bufferedReader.close();
                outputStream.close();
                dataOutputStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run() {
        while (true) {
            int ch;
            try {
                System.out.println("start run...");
                Thread.sleep(5000);
                while((ch = System.in.read())!=-1) {
                    System.out.println("ch-->" + ch);
                    byte b = (byte)ch;
                    System.out.println("b-->" + b);
                    dataOutputStream.write(b);
                    if(ch == '\n') {
                        System.out.println("flush-----");
                        dataOutputStream.flush();
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
