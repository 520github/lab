package com.sunso.lab.framework.rpc.ss.common;

/**
 * @Title:RpcResponse
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午5:26
 * @m444@126.com
 */
public class RpcResponse {
    private String requestId;
    private Throwable error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public RpcResponse setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public Throwable getError() {
        return error;
    }

    public RpcResponse setError(Throwable error) {
        this.error = error;
        return this;
    }

    public Object getResult() {
        return result;
    }

    public RpcResponse setResult(Object result) {
        this.result = result;
        return this;
    }
}
