package com.sunso.lab.framework.socker.net;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @Title:HttpHandle
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/23下午9:48
 * @m444@126.com
 */
public class HttpHandle {
    private static final String CHARSET_UTF8 = "utf-8";
    private String host;
    private int port;
    private Socket socket;
    boolean is_ssl = true;


    public HttpHandle(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        if(is_ssl) {
            socket = (SSLSocket)((SSLSocketFactory)SSLSocketFactory.getDefault()).createSocket(this.host, port);
        }
        else {
            socket = new Socket();
            socket.connect(getSocketAddress());
        }
    }

    public void get(String url) throws IOException {
        BufferedWriter bufferedWriter = getBufferedWriter();
        writeMethod(bufferedWriter, "GET", url);
        writeHost(bufferedWriter);
        writeNewLine(bufferedWriter);
        bufferedWriter.flush();

        BufferedReader bufferedReader = getBufferedReader();
        String line = null;
        while((line = bufferedReader.readLine())!= null) {
            System.out.println(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }

    public void post(String url, String data) throws IOException {
        BufferedWriter bufferedWriter = getBufferedWriter();
        writeMethod(bufferedWriter, "POST", url);
        writeHost(bufferedWriter);
        writeContentLength(bufferedWriter, data);
        writeContentType(bufferedWriter, "application/x-www-form-urlencoded");
        writeNewLine(bufferedWriter);
        bufferedWriter.write(data);
        writeNewLine(bufferedWriter);
        bufferedWriter.flush();

        BufferedReader bufferedReader = getBufferedReader();
        String line = null;
        while((line = bufferedReader.readLine())!= null) {
            System.out.println(line);
        }
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
    }

    private void writeMethod(BufferedWriter bufferedWriter, String method, String path) throws IOException {
        bufferedWriter.write(method+" " + path + " HTTP/1.1\r\n");
    }

    private void writeHost(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("Host: " + this.host + "\r\n");
    }

    private void writeContentLength(BufferedWriter bufferedWriter, String data) throws IOException {
        bufferedWriter.write("Content-Length: " + data.length() + "\r\n");
    }

    private void writeContentType(BufferedWriter bufferedWriter, String contentType) throws IOException {
        bufferedWriter.write("Content-Type: " +contentType + "\r\n");
    }

    private void writeNewLine(BufferedWriter bufferedWriter) throws IOException {
        bufferedWriter.write("\r\n");
    }

    private BufferedWriter getBufferedWriter() throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        return bufferedWriter;
    }

    private BufferedReader getBufferedReader() throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(socket.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, CHARSET_UTF8));
        return bufferedReader;
    }

    private SocketAddress getSocketAddress() {
        return new InetSocketAddress(this.host, this.port);
    }

    public static void main(String[] args) throws IOException {
        int port = 443;
        port = 80;
        HttpHandle handle = new HttpHandle("111.13.100.91", port);
        handle.get("/");
        //handle.post("/", "id=1");
    }

}
