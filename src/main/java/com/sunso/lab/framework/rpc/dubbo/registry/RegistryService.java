package com.sunso.lab.framework.rpc.dubbo.registry;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.util.List;

/**
 * @Title:RegistryService
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10下午12:06
 * @m444@126.com
 */
public interface RegistryService {

    void register(URL url);

    void unregister(URL url);

    void subscribe(URL url, NotifyListener listener);

    void unsubscribe(URL url, NotifyListener listener);

    List<URL> lookup(URL url);
}
