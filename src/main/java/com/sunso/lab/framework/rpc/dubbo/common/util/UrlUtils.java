package com.sunso.lab.framework.rpc.dubbo.common.util;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:UrlUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/10下午12:14
 * @m444@126.com
 */
public class UrlUtils {

    private final static String URL_PARAM_STARTING_SYMBOL = "?";

    public static List<URL> parseURLs(String address, Map<String, String> defaults) {
        if (address == null || address.length() == 0) {
            return null;
        }

        String[] addresses = Constants.REGISTRY_SPLIT_PATTERN.split(address);
        if (addresses == null || addresses.length == 0) {
            return null; //here won't be empty
        }

        List<URL> registries = new ArrayList<URL>();
        for (String addr : addresses) {
            registries.add(parseURL(addr, defaults));
        }
        return registries;
    }

    public static URL parseURL(String address, Map<String, String> defaults) {
        if (address == null || address.length() == 0) {
            return null;
        }

        String url;
        if (address.contains("://") || address.contains(URL_PARAM_STARTING_SYMBOL)) {
            url = address;
        }
        else {
            String[] addresses = Constants.COMMA_SPLIT_PATTERN.split(address);
            url = addresses[0];
            if (addresses.length > 1) {
                StringBuilder backup = new StringBuilder();
                for (int i = 1; i < addresses.length; i++) {
                    if (i > 1) {
                        backup.append(",");
                    }
                    backup.append(addresses[i]);
                }
                url += URL_PARAM_STARTING_SYMBOL + Constants.BACKUP_KEY + "=" + backup.toString();
            }
        }

        String defaultProtocol = defaults == null ? null : defaults.get("protocol");
        if (defaultProtocol == null || defaultProtocol.length() == 0) {
            defaultProtocol = "dubbo";
        }

        String defaultUsername = defaults == null ? null : defaults.get("username");
        String defaultPassword = defaults == null ? null : defaults.get("password");

        int defaultPort = StringUtils.parseInteger(defaults == null ? null : defaults.get("port"));
        String defaultPath = defaults == null ? null : defaults.get("path");

        Map<String, String> defaultParameters = defaults == null ? null : new HashMap<String, String>(defaults);
        if (defaultParameters != null) {
            defaultParameters.remove("protocol");
            defaultParameters.remove("username");
            defaultParameters.remove("password");
            defaultParameters.remove("host");
            defaultParameters.remove("port");
            defaultParameters.remove("path");
        }

        URL u = URL.valueOf(url);
        boolean changed = false;
        String protocol = u.getProtocol();
        String username = u.getUsername();
        String password = u.getPassword();
        String host = u.getHost();
        int port = u.getPort();
        String path = u.getPath();

        Map<String, String> parameters = new HashMap<String, String>(u.getParameters());

        if ((protocol == null || protocol.length() == 0)
                && defaultProtocol != null && defaultProtocol.length() > 0) {
            changed = true;
            protocol = defaultProtocol;
        }

        if ((username == null || username.length() == 0)
                && defaultUsername != null && defaultUsername.length() > 0) {
            changed = true;
            username = defaultUsername;
        }

        if ((password == null || password.length() == 0)
                && defaultPassword != null && defaultPassword.length() > 0) {
            changed = true;
            password = defaultPassword;
        }

        if (port <= 0) {
            if (defaultPort > 0) {
                changed = true;
                port = defaultPort;
            } else {
                changed = true;
                port = 9090;
            }
        }

        if (path == null || path.length() == 0) {
            if (defaultPath != null && defaultPath.length() > 0) {
                changed = true;
                path = defaultPath;
            }
        }

        if (defaultParameters != null && defaultParameters.size() > 0) {
            for (Map.Entry<String, String> entry : defaultParameters.entrySet()) {
                String key = entry.getKey();
                String defaultValue = entry.getValue();
                if (defaultValue != null && defaultValue.length() > 0) {
                    String value = parameters.get(key);
                    if (value == null || value.length() == 0) {
                        changed = true;
                        parameters.put(key, defaultValue);
                    }
                }
            }
        }

        if (changed) {
            u = new URL(protocol, username, password, host, port, path, parameters);
        }
        return u;
    }
}
