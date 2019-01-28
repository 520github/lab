package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.util.Map;

/**
 * @Title:Invocation
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午3:38
 * @m444@126.com
 */
public interface Invocation {
    String getMethodName();

    Class<?>[] getParameterTypes();

    //Class<?> getParametreTypes();

    Object[] getArguments();

    Map<String, String> getAttachments();

    String getAttachment(String key);

    String getAttachment(String key, String defaultValue);

    Invoker<?> getInvoker();

}
