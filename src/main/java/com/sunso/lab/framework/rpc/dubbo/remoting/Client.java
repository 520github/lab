package com.sunso.lab.framework.rpc.dubbo.remoting;

/**
 * @Title:Client
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8下午3:19
 * @m444@126.com
 */
public interface Client extends Endpoint, Channel {
    /**
     * reconnect.
     */
    void reconnect() throws RemotingException;
}
