package com.sunso.lab.framework.proxy;

/**
 * @Title:SunsoAopProxy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午5:24
 * @miaoxuehui@panda-fintech.com
 */
public interface SunsoAopProxy {

    Object getProxy(Object targetObject, SunsoInterceptor interceptor);

    Object getProxy(Object targetObject);

    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}
