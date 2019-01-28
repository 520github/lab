package com.sunso.lab.framework.rpc.dubbo.common.serialize;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Title:Serialization
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5上午10:32
 * @m444@126.com
 */
public interface Serialization {

    byte getContentTypeId();

    String getContentType();

    ObjectOutput serialize(URL url, OutputStream output) throws IOException;

    ObjectInput deserialize(URL url, InputStream inputStream) throws IOException;
}
