package com.sunso.lab.framework.rpc.dubbo.rpc.service;

import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;

/**
 * @Title:GenericException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/11下午3:19
 * @m444@126.com
 */
public class GenericException extends RuntimeException {
    private String exceptionClass;

    private String exceptionMessage;

    public GenericException() {
    }

    public GenericException(String exceptionClass, String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionClass = exceptionClass;
        this.exceptionMessage = exceptionMessage;
    }

    public GenericException(Throwable cause) {
        super(StringUtils.toString(cause));
        this.exceptionClass = cause.getClass().getName();
        this.exceptionMessage = cause.getMessage();
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
