package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.lang.reflect.Field;

/**
 * @Title:RpcResult
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午10:36
 * @m444@126.com
 */
public class RpcResult extends AbstractResult {

    private static final long serialVersionUID = 1L;


    public RpcResult() {
    }

    public RpcResult(Object result) {
        this.result = result;
    }

    public RpcResult(Throwable exception) {
        this.exception = exception;
    }


    @Override
    public Object getValue() {
        return result;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    @Override
    public boolean hasException() {
        return exception != null;
    }

    @Override
    public Object recreate() throws Throwable {
        if(exception != null) {
            try {
                Class clazz = exception.getClass();
                while (!clazz.getName().equals(Throwable.class.getName())) {
                    clazz = clazz.getSuperclass();
                }

                // get stackTrace value
                Field stackTraceField = clazz.getDeclaredField("stackTrace");
                stackTraceField.setAccessible(true);
                Object stackTrace = stackTraceField.get(exception);
                if (stackTrace == null) {
                    exception.setStackTrace(new StackTraceElement[0]);
                }
            }catch (Exception  e) {
                // ignore
            }
            throw exception;
        }
        return result;
    }

    @Override
    public Object getResult() {
        return getValue();
    }

    public void setException(Throwable e) {
        this.exception = e;
    }

    public void setValue(Object value) {
        this.result = value;
    }

    @Deprecated
    public void setResult(Object result) {
        setValue(result);
    }
}
