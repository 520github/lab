package com.sunso.lab.framework.rpc.dubbo.common.bytecode;

/**
 * @Title:NoSuchPropertyException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10下午4:07
 * @m444@126.com
 */
public class NoSuchPropertyException extends RuntimeException {
    public NoSuchPropertyException() {
        super();
    }

    public NoSuchPropertyException(String msg) {
        super(msg);
    }
}
