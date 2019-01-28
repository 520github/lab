package com.sunso.lab.framework.rpc.dubbo.common;

import java.util.regex.Pattern;

/**
 * @Title:Constants
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午7:50
 * @m444@126.com
 */
public class Constants {
    public static final String MONITOR_KEY = "monitor";
    public static final String PROTOCOL_KEY = "protocol";
    public static final String REGISTER_IP_KEY = "register.ip";

    public static final String INPUT_KEY = "input";
    public static final String OUTPUT_KEY = "output";

    public static final String EXPORT_KEY = "export";
    public static final String REFER_KEY = "refer";
    public static final String PROXY_KEY = "proxy";
    public static final String DYNAMIC_KEY = "dynamic";

    public static final String FUTURE_RETURNTYPE_KEY = "future_returntype";

    public static final String INTERFACES = "interfaces";

    public static final String SCOPE_KEY = "scope";
    public static final String SCOPE_LOCAL = "local";
    public static final String SCOPE_REMOTE = "remote";
    public static final String SCOPE_NONE = "none";

    public static final String MULTICAST = "multicast";

    public static final String DUBBO_IP_TO_BIND = "DUBBO_IP_TO_BIND";
    public static final String DUBBO_IP_TO_REGISTRY = "DUBBO_IP_TO_REGISTRY";
    public static final String DUBBO_PORT_TO_BIND = "DUBBO_PORT_TO_BIND";
    public static final String DUBBO_PORT_TO_REGISTRY = "DUBBO_PORT_TO_REGISTRY";

    public static final String LOCAL_PROTOCOL = "injvm";
    public static final String TOKEN_KEY = "token";

    public static final String GENERIC_KEY = "generic";
    public static final String ANY_VALUE = "*";

    public static final String GENERIC_SERIALIZATION_NATIVE_JAVA = "nativejava";

    public static final String GENERIC_SERIALIZATION_DEFAULT = "true";

    public static final String GENERIC_SERIALIZATION_BEAN = "bean";

    public static final String PROVIDER_SIDE = "provider";

    public static final String REGISTER_KEY = "register";
    public static final String SUBSCRIBE_KEY = "subscribe";
    public static final String REGISTRY_PROTOCOL = "registry";
    public static final String REGISTRY_KEY = "registry";

    public static final String BACKUP_KEY = "backup";
    public static final Pattern COMMA_SPLIT_PATTERN = Pattern
            .compile("\\s*[,]+\\s*");
    public static final Pattern REGISTRY_SPLIT_PATTERN = Pattern
            .compile("\\s*[|;]+\\s*");

    public static final String PID_KEY = "pid";
    public static final String TIMESTAMP_KEY = "timestamp";

    public static final String DEFAULT_KEY = "default";

    public static final String DUBBO_PROPERTIES_KEY = "dubbo.properties.file";
    public static final String DEFAULT_DUBBO_PROPERTIES = "dubbo.properties";

    public static final String ON_CONNECT_KEY = "onconnect";
    public static final String ON_DISCONNECT_KEY = "ondisconnect";


    public static final String ASYNC_KEY = "async";

    /**
     * ticks per wheel. Currently only contains two tasks, so 16 locations are enough
     */
    public static final int TICKS_PER_WHEEL = 16;

    /**
     * Every heartbeat duration / HEATBEAT_CHECK_TICK, check if a heartbeat should be sent. Every heartbeat timeout
     * duration / HEATBEAT_CHECK_TICK, check if a connection should be closed on server side, and if reconnect on
     * client side
     */
    public static final int HEARTBEAT_CHECK_TICK = 3;

    /**
     * the least heartbeat during is 1000 ms.
     */
    public static final long LEAST_HEARTBEAT_DURATION = 1000;

    public static final String EXCHANGER_KEY = "exchanger";
    public static final String DEFAULT_EXCHANGER = "header";

    /**
     * The limit of callback service instances for one interface on every client
     */
    public static final String CALLBACK_INSTANCES_LIMIT_KEY = "callbacks";
    public static final String IS_CALLBACK_SERVICE = "is_callback_service";

    /**
     * The default limit number for callback service instances
     *
     * @see #CALLBACK_INSTANCES_LIMIT_KEY
     */
    public static final int DEFAULT_CALLBACK_INSTANCES = 1;


    public static final String METHODS_KEY = "methods";

    public static final String CHANNEL_CALLBACK_KEY = "channel.callback.invokers.key";
    public static final String CALLBACK_SERVICE_PROXY_KEY = "callback.service.proxy";
    public static final String CALLBACK_SERVICE_KEY = "callback.service.instid";

    public static final String IS_SERVER_KEY = "isserver";

    public static final String DEFAULT_KEY_PREFIX = "default.";
    public static final String THREAD_NAME_KEY = "threadname";

    public static final String BIND_IP_KEY = "bind.ip";
    public static final String BIND_PORT_KEY = "bind.port";

    public static final String VERSION_KEY = "version";
    public static final String GROUP_KEY = "group";

    public static final String ANYHOST_KEY = "anyhost";
    public static final String ANYHOST_VALUE = "0.0.0.0";

    public static final int DEFAULT_TIMEOUT = 1000;
    public static final String TIMEOUT_KEY = "timeout";

    public static final String CHANNEL_READONLYEVENT_SENT_KEY = "channel.readonly.sent";
    public static final String CHANNEL_SEND_READONLYEVENT_KEY = "channel.readonly.send";
    public static final String CHANNEL_ATTRIBUTE_READONLY_KEY = "channel.readonly";

    public static final String HEARTBEAT_TIMEOUT_KEY = "heartbeat.timeout";
    public static final String HEARTBEAT_KEY = "heartbeat";
    public static final int DEFAULT_HEARTBEAT = 60 * 1000;
    public static final String SERVER_KEY = "server";
    public static final String DEFAULT_REMOTING_SERVER = "netty";

    public static final String CODEC_KEY = "codec";
    public static final String CLIENT_KEY = "client";

    public static final int DEFAULT_PAYLOAD = 8 * 1024 * 1024;
    public static final String PAYLOAD_KEY = "payload";
    public static final String SIDE_KEY = "side";

    public static final String INTERFACE_KEY = "interface";

    public static final String SERIALIZATION_KEY = "serialization";
    public static final String DEFAULT_REMOTING_SERIALIZATION = "hessian2";

    public static final String CHARSET_KEY = "charset";
    public static final String DEFAULT_CHARSET = "UTF-8";

    public static final String DECODE_IN_IO_THREAD_KEY = "decode.in.io";
    public static final boolean DEFAULT_DECODE_IN_IO_THREAD = true;

    public static final String DUBBO_VERSION_KEY = "dubbo";
    public static final String PATH_KEY = "path";


    public static final boolean DEFAULT_STUB_EVENT = false;
    public static final String STUB_EVENT_KEY = "dubbo.stub.event";
    public static final String STUB_EVENT_METHODS_KEY = "dubbo.stub.event.methods";

}
