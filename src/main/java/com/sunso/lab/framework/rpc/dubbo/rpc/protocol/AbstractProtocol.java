package com.sunso.lab.framework.rpc.dubbo.rpc.protocol;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.util.ConcurrentHashSet;
import com.sunso.lab.framework.rpc.dubbo.rpc.Exporter;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.Protocol;
import com.sunso.lab.framework.rpc.dubbo.rpc.support.ProtocolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Title:AbstractProtocol
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午4:21
 * @m444@126.com
 */
public abstract class AbstractProtocol implements Protocol {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final Map<String, Exporter<?>> exporterMap = new ConcurrentHashMap<String, Exporter<?>>();

    protected final Set<Invoker<?>> invokers = new ConcurrentHashSet<Invoker<?>>();

    protected static String serviceKey(URL url) {
        int port = url.getParameter(Constants.BIND_PORT_KEY, url.getPort());
        return serviceKey(port, url.getPath(), url.getParameter(Constants.VERSION_KEY),
                url.getParameter(Constants.GROUP_KEY));
    }

    protected static String serviceKey(int port, String serviceName, String serviceVersion, String serviceGroup) {
        return ProtocolUtils.serviceKey(port, serviceName, serviceVersion, serviceGroup);
    }

    public void destroy() {
        for(Invoker<?> invoker: invokers) {
            if(invoker != null) {
                invokers.remove(invoker);
            }
            try{
                invoker.destroy();
            }catch (Throwable t) {
                logger.warn(t.getMessage(), t);
            }
        }

        for(String key: new ArrayList<>(exporterMap.keySet())) {
            Exporter<?> exporter = exporterMap.remove(key);
            if(exporter != null) {
                try{
                    exporter.unexport();
                }catch (Throwable t) {
                    logger.warn(t.getMessage(), t);
                }
            }
        }
    }

}
