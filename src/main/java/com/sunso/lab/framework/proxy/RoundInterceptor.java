package com.sunso.lab.framework.proxy;

/**
 * @Title:RoundInterceptor
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午9:20
 * @miaoxuehui@panda-fintech.com
 */
public class RoundInterceptor implements SunsoInterceptor {

    public Object intercept(ProxyMethodInvocation invocation) throws Throwable {
        System.out.println("before intercept....");
        Object object = invocation.proceed();
        System.out.println("after intercept....");
        return object;
    }
}
