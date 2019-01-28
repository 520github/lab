package com.sunso.lab.framework.rpc.dubbo.rpc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:AbstractResult
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/5下午10:00
 * @m444@126.com
 */
public abstract class AbstractResult implements Result {
    protected Map<String, String> attachments = new HashMap<String, String>();

    protected Object result;

    protected Throwable exception;

    @Override
    public Map<String, String> getAttachments() {
        return attachments;
    }

    @Override
    public void setAttachments(Map<String, String> map) {
        this.attachments = map == null ? new HashMap<String, String>() : map;
    }

    @Override
    public void addAttachments(Map<String, String> map) {
        if (map == null) {
            return;
        }
        if (this.attachments == null) {
            this.attachments = new HashMap<String, String>();
        }
        this.attachments.putAll(map);
    }

    @Override
    public String getAttachment(String key) {
        return attachments.get(key);
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        String result = attachments.get(key);
        if (result == null || result.length() == 0) {
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setAttachment(String key, String value) {
        attachments.put(key, value);
    }
}
