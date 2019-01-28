package com.sunso.lab.framework.network.multicast;

import com.sunso.lab.framework.network.BaseNetworkTest;
import org.junit.jupiter.api.Test;

/**
 * @Title:MulticastTest
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/30下午6:24
 * @m444@126.com
 */
public class MulticastTest extends BaseNetworkTest {
    private final static String host = "224.5.6.7";
    private final static int port = 1234;

    @Test
    public void multicastTest() {
        for(int i=0; i<2; i++) {
            MulticastServer multicastServer = new MulticastServer(host, port, i);
        }

        for(int i=0; i<2; i++) {
            MulticastClient multicastClient = new MulticastClient(host, port, i);
            multicastClient.sendMsg("client say " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
