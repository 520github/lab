package com.sunso.lab.framework.rpc.dubbo.rpc.support;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;

/**
 * @Title:ProtocolUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/3下午5:37
 * @m444@126.com
 */
public class ProtocolUtils {

    public static String serviceKey(int port, String serviceName, String serviceVersion, String serviceGroup) {
        StringBuilder buf = new StringBuilder();
        if (serviceGroup != null && serviceGroup.length() > 0) {
            buf.append(serviceGroup);
            buf.append("/");
        }
        buf.append(serviceName);
        buf.append(serviceName);
        if (serviceVersion != null && serviceVersion.length() > 0 && !"0.0.0".equals(serviceVersion)) {
            buf.append(":");
            buf.append(serviceVersion);
        }
        buf.append(":");
        buf.append(port);

        return buf.toString();
    }

    public static boolean isGeneric(String generic) {
        return generic != null
                && !"".equals(generic)
                && (Constants.GENERIC_SERIALIZATION_DEFAULT.equalsIgnoreCase(generic)  /* Normal generalization cal */
                || Constants.GENERIC_SERIALIZATION_NATIVE_JAVA.equalsIgnoreCase(generic) /* Streaming generalization call supporting jdk serialization */
                || Constants.GENERIC_SERIALIZATION_BEAN.equalsIgnoreCase(generic));
    }
}
