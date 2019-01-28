package com.sunso.lab.framework.rpc.dubbo.rpc;

/**
 * @Title:Exporter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午4:16
 * @m444@126.com
 */
public interface Exporter<T> {

    Invoker<T> getInvoker();

    void unexport();
}
