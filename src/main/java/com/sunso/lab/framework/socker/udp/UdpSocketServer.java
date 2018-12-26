package com.sunso.lab.framework.socker.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Title:UdpSocketServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/24下午7:25
 * @m444@126.com
 */
public class UdpSocketServer {
    int port = 9999;
    private DatagramSocket socket;

    public void startDatagramSocket() throws IOException {
        socket = new DatagramSocket(port);
        DatagramPacket packet = readData();
        writeData(packet);
    }

    private DatagramPacket readData() throws IOException {
        byte[]  data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data, data.length);
        socket.receive(packet);
        String msg = new String(data, 0, packet.getLength());
        System.out.println("msg-->" + msg);
        return packet;
    }

    private void writeData(DatagramPacket packet) throws IOException {
        InetAddress inetAddress = packet.getAddress();
        int port = packet.getPort();
        System.out.println("hostAddress-->" + inetAddress.getHostAddress());
        System.out.println("port-->" + port);
        byte data[] = "hello,i am server".getBytes();
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, inetAddress, port);
        socket.send(sendPacket);
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new UdpSocketServer().startDatagramSocket();
    }

}
