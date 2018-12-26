package com.sunso.lab.framework.socker.hello;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Title:HelloSockerServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/19下午7:23
 * @m444@126.com
 */
public class HelloSockerServer {

    ServerSocket serverSocket = null;
    int port = 9080;
    int max = 1;

    public static void main(String[] args) {
        HelloSockerServer helloSockerServer = new HelloSockerServer();
        helloSockerServer.startServer();
    }


    public void startServer() {
        try {
            serverSocket = new ServerSocket(port, max, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {
            Socket socket = null;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                socket = serverSocket.accept();
                System.out.println("accept client---> " );

                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                String requst = getInputStreamContent(inputStream);
                System.out.println("request-->" + requst);

                writeOutputStreamData(outputStream);

                // socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getInputStreamContent(InputStream inputStream) {
        byte[] buffer = new byte[2048];
        int i;
        try {
            i = inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        StringBuffer request = new StringBuffer(2048);
        for(int j=0; j<i; j++) {
            request.append((char)buffer[j]);
        }
        return request.toString();
    }

    public void writeOutputStreamData(OutputStream outputStream) {
        DataOutputStream dos = new DataOutputStream(outputStream);
        String header="Content-Type: text/html; charset=utf-8 \r\n"
                +"Content-length: 121 \r\n\r\n";
        try {
            //dos.writeBytes(header);

            dos.writeBytes("hello");
            dos.flush();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
