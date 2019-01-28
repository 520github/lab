package com.sunso.lab.framework.rpc.dubbo.registry;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.util.List;

/**
 * @Title:NotifyListener
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10下午12:05
 * @m444@126.com
 */
public interface NotifyListener {

    void notify(List<URL> urls);
}
