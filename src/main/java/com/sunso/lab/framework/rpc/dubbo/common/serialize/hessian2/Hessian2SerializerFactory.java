package com.sunso.lab.framework.rpc.dubbo.common.serialize.hessian2;

import com.alibaba.com.caucho.hessian.io.SerializerFactory;

/**
 * @Title:Hessian2SerializerFactory
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午1:31
 * @m444@126.com
 */
public class Hessian2SerializerFactory extends SerializerFactory {

    public static final SerializerFactory SERIALIZER_FACTORY = new Hessian2SerializerFactory();

    private Hessian2SerializerFactory() {
    }

    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
