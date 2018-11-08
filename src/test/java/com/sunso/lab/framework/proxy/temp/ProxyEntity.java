package com.sunso.lab.framework.proxy.temp;

/**
 * @Title:ProxyEntity
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: panda-fintech
 * @Created on 2018/11/5下午9:35
 * @miaoxuehui@panda-fintech.com
 */
public class ProxyEntity implements Entity {

    public void helloProxy() {
        System.out.println("hello proxy");
    }

    public void helloEntity() {
        System.out.println("hello entity");
    }
}
