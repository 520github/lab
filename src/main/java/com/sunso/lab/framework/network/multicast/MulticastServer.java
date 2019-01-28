package com.sunso.lab.framework.network.multicast;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

/**
 * @Title:MulticastServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/30下午6:00
 * @m444@126.com
 */
public class MulticastServer {
    private final InetAddress inetAddress;
    private final MulticastSocket multicastSocket;
    private final String multicastHost;
    private final int multicastPort;
    private final int index;

    public MulticastServer(String multicastHost, int multicastPort, int index) {
        this.multicastHost = multicastHost;
        this.multicastPort = multicastPort;
        this.index = index;
        try {
            inetAddress = InetAddress.getByName(multicastHost);
            multicastSocket = new MulticastSocket(multicastPort);
            multicastSocket.setLoopbackMode(false);
            multicastSocket.joinGroup(inetAddress);
            startReceiveThread();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    private void startReceiveThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                byte[] buf = new byte[2048];
                DatagramPacket recv = new DatagramPacket(buf, buf.length);
                while(!multicastSocket.isClosed()) {
                    try {
                        multicastSocket.receive(recv);
                        String msg = new String(recv.getData()).trim();
                        int line = msg.indexOf('\n');
                        if(line > 0) {
                            msg = msg.substring(0, line).trim();
                        }
                        System.out.println(Thread.currentThread().getName()+ " receive msg-->" + msg);

                        sendMsg(msg);

                        Arrays.fill(buf, (byte) 0);
                    }catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "multicast-server-" + index);
        thread.setDaemon(true);
        thread.start();
    }

    public void sendMsg(String msg) {
        try {
            Thread.sleep(3000);
            byte[] data = (msg + "\n").getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, multicastPort);
            multicastSocket.send(datagramPacket);
        }catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
