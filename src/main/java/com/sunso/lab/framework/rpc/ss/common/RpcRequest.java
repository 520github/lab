package com.sunso.lab.framework.rpc.ss.common;

/**
 * @Title:RpcRequest
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午5:24
 * @m444@126.com
 */
public class RpcRequest {
    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public RpcRequest setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public RpcRequest setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public RpcRequest setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public RpcRequest setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public RpcRequest setParameters(Object[] parameters) {
        this.parameters = parameters;
        return this;
    }
}
