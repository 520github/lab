package com.sunso.lab.framework.rpc.dubbo.config;

import java.util.List;

/**
 * @Title:AbstractServiceConfig
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10上午10:47
 * @m444@126.com
 */
public abstract class AbstractServiceConfig extends AbstractInterfaceConfig {

    protected List<ProtocolConfig> protocols;
    // version
    protected String version;
    // whether to use token
    protected String token;
    // whether to export the service
    protected Boolean export;

    // delay service exporting
    protected Integer delay;


    public Boolean getExport() {
        return export;
    }

    public void setExport(Boolean export) {
        this.export = export;
    }

    public Integer getDelay() {
        return delay;
    }

    public void setDelay(Integer delay) {
        this.delay = delay;
    }
}
