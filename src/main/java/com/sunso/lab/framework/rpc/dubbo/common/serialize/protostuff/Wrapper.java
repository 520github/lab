package com.sunso.lab.framework.rpc.dubbo.common.serialize.protostuff;

/**
 * @Title:Wrapper
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午3:28
 * @m444@126.com
 */
public class Wrapper<T> {
    private T data;

    Wrapper(T data) {
        this.data = data;
    }

    Object getData() {
        return data;
    }
}
