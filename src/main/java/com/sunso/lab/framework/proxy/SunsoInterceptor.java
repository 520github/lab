package com.sunso.lab.framework.proxy;

/**
 * @Title:SunsoInterceptor
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午9:17
 * @miaoxuehui@panda-fintech.com
 */
public interface SunsoInterceptor {

    public Object intercept(ProxyMethodInvocation invocation) throws Throwable;
}
