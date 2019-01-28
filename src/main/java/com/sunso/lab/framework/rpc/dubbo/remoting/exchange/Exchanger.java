package com.sunso.lab.framework.rpc.dubbo.remoting.exchange;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;

/**
 * @Title:Exchanger
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3上午10:05
 * @m444@126.com
 */
public interface Exchanger {
    ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException;
}
