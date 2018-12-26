package com.sunso.lab.framework.socker.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * @Title:NioHttpServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/23下午4:14
 * @m444@126.com
 */
public class NioHttpServer {

    /**
     * 1、ServerSocketChannel可以通过configureBlocking方法来设置是否采用阻塞模式，设置为false后就可以调用register注册Selector，阻塞模式下不可以用Selector。
     * 2、注册后，Selector就可以通过select()来等待请求，通过参数设置等待时长，若传入参数0或者不传入参数，将会采用阻塞模式直到有请求出现。
     * 3、接收到请求后Selector调用selectedKeys方法，返回SelectedKey集合。
     * 4、SelectedKey保存了处理当前请求的Channel和Selector，并提供了不同的操作类型。四种操作属性：SelectedKey.OP_ACCEPT、SelectedKey.OP_CONNECT、SelectedKey.OP_READ、SelectedKey.OP_WRITE。
     * 5、通过SelectedKey的isAcceptable、isConnectable、isReadable和isWritable来判断操作类型，并处理相应操作。
     * 6、在相应的Handler中提取SelectedKey中的Channel和Buffer信息并执行相应操作。
     * @throws IOException
     */
    public void startServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9098));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if(selector.select(3000) == 0) {
                continue;
            }
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                System.out.println("selectionKey-->" +selectionKey.toString());
                new Thread(new NioHttpHandle(selectionKey)).run();
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new NioHttpServer().startServer();
    }
}
