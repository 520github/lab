package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.io.Serializable;
import java.util.Map;

/**
 * @Title:Result
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/12/27下午3:43
 * @m444@126.com
 */
public interface Result extends Serializable {
    Object getValue();

    Throwable getException();

    boolean hasException();

    Object recreate() throws Throwable;

    @Deprecated
    Object getResult();

    Map<String, String> getAttachments();

    void addAttachments(Map<String, String> map);

    void setAttachments(Map<String, String> map);

    String getAttachment(String key);

    String getAttachment(String key, String defaultValue);

    void setAttachment(String key, String value);
}
