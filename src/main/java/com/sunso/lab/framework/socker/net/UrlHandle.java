package com.sunso.lab.framework.socker.net;

import java.io.*;
import java.net.*;

/**
 * @Title:UrlHandle
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/23下午5:13
 * @m444@126.com
 */
public class UrlHandle {
    public static void getContentByUrl(String accessUrl) throws IOException {
        URL url = new URL(accessUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "utf-8"));
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            System.out.println(temp);
            sb.append(temp);
        }
        System.out.println(sb.toString());
        bufferedReader.close();
    }

    public static void getContentByInetSocketAddress(String accessUrl) throws IOException {
        Socket client = new Socket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("111.13.100.91", 80);
        client.connect(inetSocketAddress, 1000);
        String request = "GET /index.html HTTP/1.1\r\n" + "Host: 111.13.100.91:80\r\n";
        PrintWriter printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println(request);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"));
        StringBuilder sb = new StringBuilder();
        String temp;
        while ((temp = bufferedReader.readLine()) != null) {
            System.out.println(temp);
            sb.append(temp);
        }
        System.out.println(sb.toString());
        client.close();
    }

    public static void main(String[] args) throws IOException {
        String url = "http://www.baidu.com";
        //UrlHandle.getContentByUrl(url);
        getContentByInetSocketAddress(url);
    }
}
