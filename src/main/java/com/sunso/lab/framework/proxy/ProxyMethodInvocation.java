package com.sunso.lab.framework.proxy;

/**
 * @Title:ProxyMethodInvocation
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午5:46
 * @miaoxuehui@panda-fintech.com
 */
public interface ProxyMethodInvocation {
    Object proceed() throws Throwable;
}
