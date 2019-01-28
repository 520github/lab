package com.sunso.lab.framework.rpc.dubbo.common;

/**
 * @Title:Node
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午3:34
 * @m444@126.com
 */
public interface Node {
    URL getUrl();

    boolean isAvailable();

    void destroy();
}
