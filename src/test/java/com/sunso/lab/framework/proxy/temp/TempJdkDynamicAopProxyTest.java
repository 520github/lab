package com.sunso.lab.framework.proxy.temp;

import com.sunso.lab.framework.proxy.RoundInterceptor;
import org.junit.jupiter.api.Test;

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
}
