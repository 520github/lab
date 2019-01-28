package com.sunso.lab.framework.rpc.dubbo.remoting;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:Codec
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午10:49
 * @m444@126.com
 */
@Deprecated
public interface Codec  {
    Object NEED_MORE_INPUT = new Object();

    void encode(Channel channel, OutputStream output, Object message) throws IOException;

    Object decode(Channel channel, InputStream input) throws IOException;
}
