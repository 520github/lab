package com.sunso.lab.framework.rpc.dubbo.config;

import com.sunso.lab.framework.rpc.dubbo.config.support.Parameter;

import java.util.List;

/**
 * @Title:MethodConfig
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10下午3:33
 * @m444@126.com
 */
public class MethodConfig extends AbstractMethodConfig {
    protected String name;
    private List<ArgumentConfig> arguments;

    @Parameter(excluded = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkMethodName("name", name);
        this.name = name;
        if (id == null || id.length() == 0) {
            id = name;
        }
    }

    public List<ArgumentConfig> getArguments() {
        return arguments;
    }

    @SuppressWarnings("unchecked")
    public void setArguments(List<? extends ArgumentConfig> arguments) {
        this.arguments = (List<ArgumentConfig>) arguments;
    }
}
