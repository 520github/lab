package com.sunso.lab.framework.rpc.ss.registry;

/**
 * @Title:RegistryConstant
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午4:21
 * @m444@126.com
 */
public class RegistryConstant {
    //zk超时时间
    public static final int ZK_SESSION_TIMEOUT = 5000;
    //注册节点
    public static final String ZK_REGISTRY_PATH = "registry";
    // 数据节点
    public static final String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
}
