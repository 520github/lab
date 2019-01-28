package com.sunso.lab.framework.rpc.dubbo.config;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.Version;
import com.sunso.lab.framework.rpc.dubbo.common.util.ConfigUtils;
import com.sunso.lab.framework.rpc.dubbo.common.util.NetUtils;
import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;
import com.sunso.lab.framework.rpc.dubbo.common.util.UrlUtils;
import com.sunso.lab.framework.rpc.dubbo.monitor.MonitorService;
import com.sunso.lab.framework.rpc.dubbo.registry.RegistryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title:AbstractInterfaceConfig
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/9下午4:25
 * @m444@126.com
 */
public abstract class AbstractInterfaceConfig extends AbstractMethodConfig {

    protected ApplicationConfig application;
    // module info
    protected ModuleConfig module;

    // registry centers
    protected List<RegistryConfig> registries;

    // service monitor
    protected MonitorConfig monitor;

    protected void checkRegistry() {
        if (registries == null || registries.isEmpty()) {
            String address = ConfigUtils.getProperty("dubbo.registry.address");
            if (address != null && address.length() > 0) {
                registries = new ArrayList<RegistryConfig>();
                String[] as = address.split("\\s*[|]+\\s*");
                for (String a : as) {
                    RegistryConfig registryConfig = new RegistryConfig();
                    registryConfig.setAddress(a);
                    registries.add(registryConfig);
                }
            }
        }

        if ((registries == null || registries.isEmpty())) {
            throw new IllegalStateException((getClass().getSimpleName().startsWith("Reference")
                    ? "No such any registry to refer service in consumer "
                    : "No such any registry to export service in provider ")
                    + NetUtils.getLocalHost()
                    + " use dubbo version "
                    + Version.getVersion()
                    + ", Please add <dubbo:registry address=\"...\" /> to your spring config. If you want unregister, please set <dubbo:service registry=\"N/A\" />");
        }

        for(RegistryConfig registryConfig : registries) {
            appendProperties(registryConfig);
        }
    }

    protected List<URL> loadRegistries(boolean provider) {
        checkRegistry();
        List<URL> registryList = new ArrayList<URL>();
        if (registries == null || registries.isEmpty()) {
            return registryList;
        }

        for (RegistryConfig config : registries) {
            String address = config.getAddress();
            if (address == null || address.length() == 0) {
                address = Constants.ANYHOST_VALUE;
            }

            String sysaddress = System.getProperty("dubbo.registry.address");
            if (sysaddress != null && sysaddress.length() > 0) {
                address = sysaddress;
            }

            if (address.length() > 0 && !RegistryConfig.NO_AVAILABLE.equalsIgnoreCase(address)) {
                Map<String, String> map = new HashMap<String, String>();
                appendParameters(map, application);
                appendParameters(map, config);
                map.put("path", RegistryService.class.getName());
                map.put("dubbo", Version.getProtocolVersion());
                map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
                if (ConfigUtils.getPid() > 0) {
                    map.put(Constants.PID_KEY, String.valueOf(ConfigUtils.getPid()));
                }
                if (!map.containsKey("protocol")) {
                    map.put("protocol", "dubbo");
                    //map.put("protocol", "remote");
                }

                List<URL> urls = UrlUtils.parseURLs(address, map);
                for (URL url : urls) {
                    url = url.addParameter(Constants.REGISTRY_KEY, url.getProtocol());
                    url = url.setProtocol(Constants.REGISTRY_PROTOCOL);
                    if ((provider && url.getParameter(Constants.REGISTER_KEY, true))
                            || (!provider && url.getParameter(Constants.SUBSCRIBE_KEY, true))) {
                        registryList.add(url);
                    }
                }
            }
        }

        return registryList;
    }

    protected URL loadMonitor(URL registryURL) {
        if (monitor == null) {
            String monitorAddress = ConfigUtils.getProperty("dubbo.monitor.address");
            String monitorProtocol = ConfigUtils.getProperty("dubbo.monitor.protocol");
            if ((monitorAddress == null || monitorAddress.length() == 0)
                    && (monitorProtocol == null || monitorProtocol.length() == 0)) {
                return null;
            }

            monitor = new MonitorConfig();
            if (monitorAddress != null && monitorAddress.length() > 0) {
                monitor.setAddress(monitorAddress);
            }
            if (monitorProtocol != null && monitorProtocol.length() > 0) {
                monitor.setProtocol(monitorProtocol);
            }
        }

        appendProperties(monitor);
        Map<String, String> map = new HashMap<String, String>();
        map.put(Constants.INTERFACE_KEY, MonitorService.class.getName());
        map.put("dubbo", Version.getProtocolVersion());
        map.put(Constants.TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        if (ConfigUtils.getPid() > 0) {
            map.put(Constants.PID_KEY, String.valueOf(ConfigUtils.getPid()));
        }

        String hostToRegistry = ConfigUtils.getSystemProperty(Constants.DUBBO_IP_TO_REGISTRY);
        if (hostToRegistry == null || hostToRegistry.length() == 0) {
            hostToRegistry = NetUtils.getLocalHost();
        }
        else if (NetUtils.isInvalidLocalHost(hostToRegistry)) {
            throw new IllegalArgumentException("Specified invalid registry ip from property:" + Constants.DUBBO_IP_TO_REGISTRY + ", value:" + hostToRegistry);
        }

        map.put(Constants.REGISTER_IP_KEY, hostToRegistry);
        appendParameters(map, monitor);
        appendParameters(map, application);
        String address = monitor.getAddress();
        String sysaddress = System.getProperty("dubbo.monitor.address");
        if (sysaddress != null && sysaddress.length() > 0) {
            address = sysaddress;
        }

        if (ConfigUtils.isNotEmpty(address)) {
            if (!map.containsKey(Constants.PROTOCOL_KEY)) {
                map.put(Constants.PROTOCOL_KEY, "dubbo");
                //map.put(Constants.PROTOCOL_KEY, "logstat");
            }
            return UrlUtils.parseURL(address, map);
        }
        else if(Constants.REGISTRY_PROTOCOL.equals(monitor.getProtocol()) && registryURL != null) {
            return registryURL.setProtocol("dubbo").addParameter(Constants.PROTOCOL_KEY, "registry")
                    .addParameterAndEncoded(Constants.REFER_KEY, StringUtils.toQueryString(map));
        }
        return null;
    }
}
