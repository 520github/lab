package com.sunso.lab.framework.rpc.dubbo.config;

/**
 * @Title:RegistryConfig
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/9下午4:16
 * @m444@126.com
 */
public class RegistryConfig extends AbstractConfig {
    public static final String NO_AVAILABLE = "N/A";

    // register center address
    private String address;

    // username to login register center
    private String username;

    // password to login register center
    private String password;

    // default port for register center
    private Integer port;

    // protocol for register center
    private String protocol;

    // client impl
    private String transporter;


    public String getAddress() {
        return address;
    }

    public RegistryConfig setAddress(String address) {
        this.address = address;
        return this;
    }
}
