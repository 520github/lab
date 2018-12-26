package com.sunso.lab.framework.socker.udp;

import org.omg.PortableInterceptor.INACTIVE;

import java.io.IOException;
import java.net.*;

/**
 * @Title:UdpSocketClient
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/24下午7:56
 * @m444@126.com
 */
public class UdpSocketClient {
    private DatagramSocket socket;
    private String host = "127.0.0.1";
    int port = 9999;

    public void startUdpSocketClient() throws IOException {
        socket = new DatagramSocket();
        sendData();
        readData();
    }

    private void sendData() throws IOException {
        InetAddress address = InetAddress.getByName(host);
        byte[] data = "hello, i am client".getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }

    private void readData() throws IOException {
        byte[] data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        String msg = new String(data, 0, packet.getLength());
        System.out.println("msg-->" + msg);
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new UdpSocketClient().startUdpSocketClient();
    }
}
