package com.sunso.lab.framework.rpc.dubbo.common.util;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

/**
 * @Title:ExecutorUtil
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午7:49
 * @m444@126.com
 */
public class ExecutorUtil {
    // name+host+port
    public static URL setThreadName(URL url, String defaultName) {
        String name = url.getParameter(Constants.THREAD_NAME_KEY, defaultName);
        name = name + "-" + url.getAddress();
        url = url.addParameter(Constants.THREAD_NAME_KEY, name);
        return url;
    }
}
