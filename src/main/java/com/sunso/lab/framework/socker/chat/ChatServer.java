package com.sunso.lab.framework.socker.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Title:ChatServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/20下午2:35
 * @m444@126.com
 */
public class ChatServer {
    private ServerSocket serverSocket = null;
    private List<Socket> acceptList = new ArrayList<Socket>();

    public ChatServer() {

    }

    public void startChatServer() {
        try {
            serverSocket = new ServerSocket(9080);
            ExecutorService executorService = Executors.newCachedThreadPool();
            while (true) {
                Socket client = serverSocket.accept();
                acceptList.add(client);
                executorService.execute(new ChatAcceptHandle(client, acceptList));
                Thread.sleep(3000);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer().startChatServer();
    }
}
