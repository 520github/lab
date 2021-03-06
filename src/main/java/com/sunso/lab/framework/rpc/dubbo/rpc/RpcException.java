package com.sunso.lab.framework.rpc.dubbo.rpc;

/**
 * @Title:RpcException
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午3:49
 * @m444@126.com
 */
public class RpcException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public static final int UNKNOWN_EXCEPTION = 0;
    public static final int NETWORK_EXCEPTION = 1;
    public static final int TIMEOUT_EXCEPTION = 2;
    public static final int BIZ_EXCEPTION = 3;
    public static final int FORBIDDEN_EXCEPTION = 4;
    public static final int SERIALIZATION_EXCEPTION = 5;

    private int code;

    public RpcException(String message) {
        super(message);
    }

    public RpcException() {
        super();
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    public RpcException(int code) {
        super();
        this.code = code;
    }

    public  RpcException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public RpcException(int code, String message) {
        super(message);
        this.code = code;
    }

    public  RpcException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public RpcException setCode(int code) {
        this.code = code;
        return this;
    }

    public boolean isBiz() {
        return code == BIZ_EXCEPTION;
    }

    public boolean isForbidded() {
        return code == FORBIDDEN_EXCEPTION;
    }

    public boolean isTimeout() {
        return code == TIMEOUT_EXCEPTION;
    }

    public boolean isNetwork() {
        return code == NETWORK_EXCEPTION;
    }

    public boolean isSerialization() {
        return code == SERIALIZATION_EXCEPTION;
    }

}
