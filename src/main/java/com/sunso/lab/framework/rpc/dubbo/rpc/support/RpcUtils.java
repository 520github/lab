package com.sunso.lab.framework.rpc.dubbo.rpc.support;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.util.ReflectUtils;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invocation;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.Future;

/**
 * @Title:RpcUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/6上午10:29
 * @m444@126.com
 */
public class RpcUtils {

    public static boolean isFutureReturnType(Invocation inv) {
        return Boolean.TRUE.toString().equals(inv.getAttachment(Constants.FUTURE_RETURNTYPE_KEY));
    }

    public static Type[] getReturnTypes(Invocation invocation) {
        try{
            if (invocation != null && invocation.getInvoker() != null
                    && invocation.getInvoker().getUrl() != null
                    && !invocation.getMethodName().startsWith("$")) {
                String service = invocation.getInvoker().getUrl().getServiceInterface();
                if (service != null && service.length() > 0) {
                    Class<?> invokerInterface = invocation.getInvoker().getInterface();
                    Class<?> cls = invokerInterface != null ?
                            ReflectUtils.forName(invokerInterface.getClassLoader(), service)
                            : ReflectUtils.forName(service);

                    Method method = cls.getMethod(invocation.getMethodName(), invocation.getParameterTypes());
                    if (method.getReturnType() == void.class) {
                        return null;
                    }
                    Class<?> returnType = method.getReturnType();
                    Type genericReturnType = method.getGenericReturnType();
                    if (Future.class.isAssignableFrom(returnType)) {
                        if (genericReturnType instanceof ParameterizedType) {
                            Type actualArgType = ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
                            if (actualArgType instanceof ParameterizedType) {
                                returnType = (Class<?>) ((ParameterizedType) actualArgType).getRawType();
                                genericReturnType = actualArgType;
                            }
                            else {
                                returnType = (Class<?>) actualArgType;
                                genericReturnType = returnType;
                            }
                        }
                        else {
                            returnType = null;
                            genericReturnType = null;
                        }
                    }
                    return new Type[]{returnType, genericReturnType};
                }
            }
        }catch (Throwable t) {
            //logger.warn(t.getMessage(), t);
        }
        return null;
    }
}
