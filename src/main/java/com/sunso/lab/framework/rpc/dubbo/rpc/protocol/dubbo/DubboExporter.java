package com.sunso.lab.framework.rpc.dubbo.rpc.protocol.dubbo;

import com.sunso.lab.framework.rpc.dubbo.rpc.Exporter;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.protocol.AbstractExporter;

import java.util.Map;

/**
 * @Title:DubboExporter
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午5:52
 * @m444@126.com
 */
public class DubboExporter<T> extends AbstractExporter<T> {

    private final String key;
    private final Map<String, Exporter<?>> exporterMap;

    public DubboExporter(Invoker<T> invoker, String key, Map<String, Exporter<?>> exporterMap) {
        super(invoker);
        this.key = key;
        this.exporterMap = exporterMap;
    }

    public void unexport() {
        super.unexport();
        exporterMap.remove(key);
    }

}
