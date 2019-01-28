package com.sunso.lab.framework.rpc.dubbo.config;

import com.sunso.lab.framework.rpc.dubbo.config.support.Parameter;

/**
 * @Title:MonitorConfig
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午7:47
 * @m444@126.com
 */
public class MonitorConfig extends AbstractConfig {
    private String protocol;

    private String address;


    @Parameter(excluded = true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Parameter(excluded = true)
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
