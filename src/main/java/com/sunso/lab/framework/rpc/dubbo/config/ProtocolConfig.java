package com.sunso.lab.framework.rpc.dubbo.config;

import com.sunso.lab.framework.rpc.dubbo.config.support.Parameter;

/**
 * @Title:ProtocolConfig
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/9下午4:23
 * @m444@126.com
 */
public class ProtocolConfig extends AbstractConfig {
    // service IP addresses (used when there are multiple network cards available)
    private String host;
    // context path
    private String contextpath;
    // protocol name
    private String name;
    // whether to register
    private Boolean register;

    private Integer port;

    @Parameter(excluded = true)
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Parameter(excluded = true)
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Parameter(excluded = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkName("name", name);
        this.name = name;
        if (id == null || id.length() == 0) {
            id = name;
        }
    }

    public void setRegister(Boolean register) {
        this.register = register;
    }

    @Parameter(excluded = true)
    public String getContextpath() {
        return contextpath;
    }

    public void setContextpath(String contextpath) {
        checkPathName("contextpath", contextpath);
        this.contextpath = contextpath;
    }
}
