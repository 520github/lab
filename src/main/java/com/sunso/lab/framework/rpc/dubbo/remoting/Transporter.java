package com.sunso.lab.framework.rpc.dubbo.remoting;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:Transporter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/1上午11:49
 * @m444@126.com
 */
public interface Transporter {
    Server bind(URL url, ChannelHandler handler);
}
