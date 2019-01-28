package com.sunso.lab.framework.rpc.dubbo.common.util;

import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.io.IOException;
import java.net.*;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @Title:NetUtils
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午2:34
 * @m444@126.com
 */
public class NetUtils {
    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final int RND_PORT_START = 30000;
    private static final int RND_PORT_RANGE = 10000;

    private static final int MIN_PORT = 0;
    private static final int MAX_PORT = 65535;

    public static final String LOCALHOST = "127.0.0.1";
    public static final String ANYHOST = "0.0.0.0";

    private static final Pattern LOCAL_IP_PATTERN = Pattern.compile("127(\\.\\d{1,3}){3}$");
    private static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static volatile InetAddress LOCAL_ADDRESS = null;

    public static int getAvailablePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException e) {
            return getRandomPort();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static int getAvailablePort(int port) {
        if (port <= 0) {
            return getAvailablePort();
        }
        for (int i = port; i < MAX_PORT; i++) {
            ServerSocket ss = null;
            try {
                ss = new ServerSocket(i);
                return i;
            } catch (IOException e) {
                // continue
            } finally {
                if (ss != null) {
                    try {
                        ss.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return port;
    }

    public static int getRandomPort() {
        return RND_PORT_START + RANDOM.nextInt(RND_PORT_RANGE);
    }

    public static boolean isInvalidPort(int port) {
        return port <= MIN_PORT || port > MAX_PORT;
    }

    public static String toAddressString(InetSocketAddress address) {
        return address.getAddress().getHostAddress() + ":" + address.getPort();
    }

    public static String filterLocalHost(String host) {
        if (host == null || host.length() == 0) {
            return host;
        }
        if (host.contains("://")) {
            URL u = URL.valueOf(host);
            if (NetUtils.isInvalidLocalHost(u.getHost())) {
                return u.setHost(NetUtils.getLocalHost()).toFullString();
            }
        } else if (host.contains(":")) {
            int i = host.lastIndexOf(':');
            if (NetUtils.isInvalidLocalHost(host.substring(0, i))) {
                return NetUtils.getLocalHost() + host.substring(i);
            }
        } else {
            if (NetUtils.isInvalidLocalHost(host)) {
                return NetUtils.getLocalHost();
            }
        }
        return host;
    }

    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? LOCALHOST : address.getHostAddress();
    }

    public static InetAddress getLocalAddress() {
        if (LOCAL_ADDRESS != null) {
            return LOCAL_ADDRESS;
        }
        InetAddress localAddress = getLocalAddress0();
        LOCAL_ADDRESS = localAddress;
        return localAddress;
    }

    public static boolean isInvalidLocalHost(String host) {
        return host == null
                || host.length() == 0
                || host.equalsIgnoreCase("localhost")
                || host.equals("0.0.0.0")
                || (LOCAL_IP_PATTERN.matcher(host).matches());
    }

    private static InetAddress getLocalAddress0() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if(localAddress instanceof Inet6Address) {
                Inet6Address address = (Inet6Address) localAddress;
                if(isValidV6Address(address)) {
                    return normalizeV6Address(address);
                }
            }
            else if(isValidAddress(localAddress)) {
                return localAddress;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        try{
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if(interfaces == null) {
                return localAddress;
            }

            while (interfaces.hasMoreElements()) {
                try {
                    NetworkInterface network = interfaces.nextElement();
                    Enumeration<InetAddress> addresses = network.getInetAddresses();
                    while(addresses.hasMoreElements()) {
                        try {
                            InetAddress address = addresses.nextElement();
                            if(address instanceof Inet6Address) {
                                Inet6Address v6Address = (Inet6Address) address;
                                if(isValidV6Address(v6Address)) {
                                    return normalizeV6Address(v6Address);
                                }
                            }
                            else if (isValidAddress(address)) {
                                return address;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return localAddress;
    }

    static boolean isValidV6Address(Inet6Address address) {
        boolean preferIpv6 = Boolean.getBoolean("java.net.preferIPv6Addresses");
        if (!preferIpv6) {
            return false;
        }
        try{
            return address.isReachable(100);
        }catch (Exception e) {
            // ignore
        }
        return false;
    }

    static boolean isValidAddress(InetAddress address) {
        if(address == null || address.isLoopbackAddress()) {
            return false;
        }
        String name = address.getHostAddress();
        return (name != null
                && !ANYHOST.equals(name)
                && !LOCALHOST.equals(name)
                && IP_PATTERN.matcher(name).matches());
    }

    static InetAddress normalizeV6Address(Inet6Address address) {
        String addr = address.getHostAddress();
        int i = addr.lastIndexOf('%');
        if (i > 0) {
            try {
                return InetAddress.getByName(addr.substring(0, i) + '%' + address.getScopeId());
            } catch (UnknownHostException e) {
                // ignore
                //logger.debug("Unknown IPV6 address: ", e);
            }
        }
        return address;
    }


}
