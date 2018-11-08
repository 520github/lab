package com.sunso.lab.framework.proxy.temp;

import com.sunso.lab.framework.proxy.RoundInterceptor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Title:TempJdkDynamicAopProxyTest
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午9:38
 * @miaoxuehui@panda-fintech.com
 */
public class TempJdkDynamicAopProxyTest {

    @Test
    public void getProxyTest() {
        TempJdkDynamicAopProxy proxy = new TempJdkDynamicAopProxy();
        Entity entity = new ProxyEntity();
        Entity proxyObj = (Entity)proxy.getProxy(entity, new RoundInterceptor());
        proxyObj.helloProxy();
        proxyObj.helloEntity();
    }

    @Test
    public void sourceTest() {
        Entity entity = create(Entity.class);
        entity.helloEntity();
    }

    private <T> T create(Class<?> interfaceClass) {
       return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke call....");
                return null;
            }
        });
    }

}
