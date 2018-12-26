package com.sunso.lab.framework.socker.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Title:NioHttpHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/23下午4:20
 * @m444@126.com
 */
public class NioHttpHandle implements Runnable {

    private SelectionKey selectionKey;
    private static final String DEFAULT_CHARSET = "utf-8";

    public NioHttpHandle(SelectionKey selectionKey) {
        this.selectionKey = selectionKey;
    }

    public void run() {
        try{
            if(selectionKey.isAcceptable()) {
                handleAccept();
            }
            if(selectionKey.isReadable()) {
                handleRead();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAccept() throws IOException {
        SocketChannel clientChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selectionKey.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    }

    public void handleRead() throws IOException {
        System.out.println("handler selectionKey-->" + selectionKey.toString());
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
        buffer.clear();

        if(socketChannel.read(buffer) == -1) {
            socketChannel.close();
            return;
        }

        buffer.flip();
        String msg = Charset.forName(DEFAULT_CHARSET).newDecoder().decode(buffer).toString();
        String datas[] = msg.split("\r\n");
        for(String data: datas) {
            System.out.println("" + data);
        }

        StringBuilder sb = new StringBuilder();
        sb.append("HTTP/1.1 200 OK\r\n");
        sb.append("Content-Type:text/html;Charset="+DEFAULT_CHARSET+"\r\n");
        sb.append("\r\n");
        sb.append("<html><head><title>SHOW</title></head></body>");
        sb.append("Received:<br/>");
        for(String data: datas) {
            sb.append(data + "<br/>");
        }
        sb.append("</body></html>");
        buffer = ByteBuffer.wrap(sb.toString().getBytes(DEFAULT_CHARSET));
        socketChannel.write(buffer);
        socketChannel.close();
    }


}
