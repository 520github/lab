package com.sunso.lab.framework.socker.chat;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @Title:ChatAcceptHandle
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/20下午2:42
 * @m444@126.com
 */
public class ChatAcceptHandle implements Runnable {
    private BufferedReader bufferedReader;
    private Socket socket;
    private List<Socket> acceptList;
    private String msg = "";

    public ChatAcceptHandle(Socket socket, List<Socket> acceptList) {
        this.socket = socket;
        this.acceptList = acceptList;
        try{
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            msg = "user:" + this.socket.getInetAddress() + " join chat, current online user num " +  acceptList.size();
            this.sendSmg();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                if((msg = bufferedReader.readLine()) != null) {
                    System.out.println("msg lenght-->" + msg.length());
                    if(msg.length() < 1) {
                        continue;
                    }
                    if(msg.equals("bye")) {
                        acceptList.remove(socket);
                        this.sendClientBye();
                        bufferedReader.close();

                        msg = "user " + socket.getInetAddress() + " logout chat, current online user num " + acceptList.size();
                        socket.close();
                        this.sendSmg();
                        break;
                    }
                    msg = socket.getInetAddress() + " say:" + msg;
                    this.sendSmg();
                }
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendSmg() {
        System.out.println("msg-->" + msg);
        int size = acceptList.size();
        for(int i=0; i<size; i++) {
            Socket accept = acceptList.get(i);
            if(accept == socket) {
                System.out.println("发现自己不用发消息");
                continue;
            }
            PrintWriter printWriter = null;
            try {
                printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(accept.getOutputStream())), true);
                printWriter.println(msg);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void sendClientBye() {
        try{
            PrintWriter printWriter = null;
            printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            printWriter.println("bye");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
