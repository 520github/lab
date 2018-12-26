package com.sunso.lab.framework.socker.chat;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Title:ChatClient
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/20下午3:18
 * @m444@126.com
 */
public class ChatClient implements Runnable {
    Socket socket =  null;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public void startClient() {
        try {
            socket = new Socket("localhost", 9080);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            Thread thread = new Thread(this);
            thread.start();

            //sendMessage();
            //sendMessageByScanner();
            sendMessageBySystemIn();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        while (true) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            try {
                while((line = bufferedReader.readLine()) != null) {
                    System.out.println("send line-->" + line);
                    printWriter.println(line);
                }
                Thread.sleep(10000);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMessageByScanner() {
        while(true) {
            String line = null;
            Scanner scanner = new Scanner(System.in);
            while((line = scanner.next()) != null) {
                System.out.println("send line-->" + line);
                printWriter.println(line);
            }
        }
    }

    private void sendMessageBySystemIn() {
        //while(true) {
            int ch = -1;
            try{
                byte data[] = new byte[128];
                while((ch = System.in.read(data))!=-1) {
                    if(ch != -1) {
                        String msg = new String(data, 0, ch);
                        // System.out.println("msg-->" + msg + ",---");
                        printWriter.println(msg);
                        closeWriter(msg);
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        //}
    }

    private void readMessage() {
        if(socket == null) {
            return;
        }
        if(socket.isClosed()) {
            return;
        }
        if(!socket.isConnected()) {
            return;
        }
        if(socket.isInputShutdown()) {
            return;
        }
        String line = null;
        try{
            System.out.println("start read msg");
            while((line = bufferedReader.readLine()) != null) {
                System.out.println("read line-->" + line);
                if(closeReader(line)) {
                    break;
                }
            }
            Thread.sleep(10000);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean closeWriter(String line) throws IOException {
        if(line.startsWith("bye")) {
            socket.shutdownOutput();
            return true;
        }
        return false;
    }

    private boolean closeReader(String line) throws IOException {
        if(line.startsWith("bye")) {
            //bufferedReader.close();
            socket.shutdownInput();
            return true;
        }
        return false;
    }

    public void run() {
        while (true) {
            readMessage();
        }
    }

    public static void main(String[] args) {
        new ChatClient().startClient();
    }
}
