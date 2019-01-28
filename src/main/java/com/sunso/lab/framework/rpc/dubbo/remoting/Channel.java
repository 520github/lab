package com.sunso.lab.framework.rpc.dubbo.remoting;

import java.net.InetSocketAddress;

/**
 * @Title:Channel
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1上午11:53
 * @m444@126.com
 */
public interface Channel extends Endpoint {
    InetSocketAddress getRemoteAddress();

    boolean isConnected();

    boolean hasAttribute(String key);

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    void removeAttribute(String key);
}
