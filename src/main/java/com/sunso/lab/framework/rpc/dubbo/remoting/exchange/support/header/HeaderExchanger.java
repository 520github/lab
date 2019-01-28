package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.Transporters;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeServer;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Exchanger;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.DecodeHandler;

/**
 * @Title:HeaderExchanger
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/7下午4:15
 * @m444@126.com
 */
public class HeaderExchanger implements Exchanger {
    @Override
    public ExchangeServer bind(URL url, ExchangeHandler handler) throws RemotingException {
        return new HeaderExchangeServer(Transporters.bind(url, new DecodeHandler(new HeaderExchangeHandler(handler))));
    }
}
