package com.sunso.lab.framework.rpc.dubbo.remoting;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:Dispatcher
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午3:12
 * @m444@126.com
 */
public interface Dispatcher {
    ChannelHandler dispatch(ChannelHandler handler, URL url);
}
