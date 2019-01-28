package com.sunso.lab.framework.rpc.dubbo.remoting.exchange;

/**
 * @Title:ResponseCallback
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午10:10
 * @m444@126.com
 */
public interface ResponseCallback {
    void done(Object response);

    void caught(Throwable t);
}
