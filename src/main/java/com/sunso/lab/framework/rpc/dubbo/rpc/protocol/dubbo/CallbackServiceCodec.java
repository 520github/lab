package com.sunso.lab.framework.rpc.dubbo.rpc.protocol.dubbo;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invocation;
import com.sunso.lab.framework.rpc.dubbo.rpc.Invoker;
import com.sunso.lab.framework.rpc.dubbo.rpc.RpcInvocation;

import java.io.IOException;
import java.util.Set;

/**
 * @Title:CallbackServiceCodec
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/6下午7:31
 * @m444@126.com
 */
public class CallbackServiceCodec {
    private static final byte CALLBACK_NONE = 0x0;
    private static final byte CALLBACK_CREATE = 0x1;
    private static final byte CALLBACK_DESTROY = 0x2;

    public static Object decodeInvocationArgument(
            Channel channel, RpcInvocation inv, Class<?>[] pts, int paraIndex, Object inObject)
            throws IOException {
        URL url = null;
        try{
            url = DubboProtocol.getDubboProtocol().getInvoker(channel, inv).getUrl();
        }catch (RemotingException e) {
            return inObject;
        }

        byte callbackstatus = isCallBack(url, inv.getMethodName(), paraIndex);
        switch (callbackstatus) {
            case CallbackServiceCodec.CALLBACK_NONE:
                return inObject;
            case CallbackServiceCodec.CALLBACK_CREATE:

            case CallbackServiceCodec.CALLBACK_DESTROY:

            default:
                return inObject;
        }
    }

    private static Object referOrDestroyCallbackService(Channel channel, URL url, Class<?> clazz, Invocation inv, int instid, boolean isRefer) {
        Object proxy = null;
        String invokerCacheKey = getServerSideCallbackInvokerCacheKey(channel, clazz.getName(), instid);
        String proxyCacheKey = getServerSideCallbackServiceCacheKey(channel, clazz.getName(), instid);
        proxy = channel.getAttribute(proxyCacheKey);
        String countkey = getServerSideCountKey(channel, clazz.getName());

        if(isRefer) {
            if(proxy == null) {
                URL referurl = URL.valueOf("callback://" + url.getAddress() + "/" + clazz.getName() + "?" + Constants.INTERFACE_KEY + "=" + clazz.getName());
                referurl = referurl.addParametersIfAbsent(url.getParameters()).removeParameter(Constants.METHODS_KEY);
                if(!isInstancesOverLimit(channel, referurl, clazz.getName(), instid, true)) {

                }
            }
        }
        else {
            if (proxy != null) {
                Invoker<?> invoker = (Invoker<?>) channel.getAttribute(invokerCacheKey);
                try {
                    Set<Invoker<?>> callbackInvokers = (Set<Invoker<?>>) channel.getAttribute(Constants.CHANNEL_CALLBACK_KEY);
                    if (callbackInvokers != null) {
                        callbackInvokers.remove(invoker);
                    }
                    invoker.destroy();
                }catch (Throwable t) {
                    //logger.error(e.getMessage(), e);
                }
                channel.removeAttribute(proxyCacheKey);
                channel.removeAttribute(invokerCacheKey);
                decreaseInstanceCount(channel, countkey);
            }
        }

        return proxy;
    }

    private static boolean isInstancesOverLimit(Channel channel, URL url, String interfaceClass, int instid, boolean isServer) {
        Integer count = (Integer) channel.getAttribute(isServer ? getServerSideCountKey(channel, interfaceClass) : getClientSideCountKey(interfaceClass));
        int limit = url.getParameter(Constants.CALLBACK_INSTANCES_LIMIT_KEY, Constants.DEFAULT_CALLBACK_INSTANCES);
        if (count != null && count >= limit) {
            throw new IllegalStateException("interface " + interfaceClass + " `s callback instances num exceed providers limit :" + limit
                    + " ,current num: " + (count + 1) + ". The new callback service will not work !!! you can cancle the callback service which exported before. channel :" + channel);
        }
        else {
            return false;
        }
    }

    private static void increaseInstanceCount(Channel channel, String countkey) {
        try{
            //ignore concurrent problem?
            Integer count = (Integer) channel.getAttribute(countkey);
            if (count == null) {
                count = 1;
            } else {
                count++;
            }
            channel.setAttribute(countkey, count);
        }catch (Throwable t) {
            //logger.error(e.getMessage(), e);
        }
    }

    private static void decreaseInstanceCount(Channel channel, String countkey) {
        try{
            Integer count = (Integer) channel.getAttribute(countkey);
            if (count == null || count <= 0) {
                return;
            }
            else {
                count--;
            }
            channel.setAttribute(countkey, count);
        }catch (Throwable t) {
            //logger.error(e.getMessage(), e);
        }
    }

    private static String getServerSideCallbackServiceCacheKey(Channel channel, String interfaceClass, int instid) {
        return Constants.CALLBACK_SERVICE_PROXY_KEY + "." + System.identityHashCode(channel) + "." + interfaceClass + "." + instid;
    }

    private static String getServerSideCallbackInvokerCacheKey(Channel channel, String interfaceClass, int instid) {
        return getServerSideCallbackServiceCacheKey(channel, interfaceClass, instid) + "." + "invoker";
    }

    private static String getClientSideCountKey(String interfaceClass) {
        return Constants.CALLBACK_SERVICE_KEY + "." + interfaceClass + ".COUNT";
    }

    private static String getServerSideCountKey(Channel channel, String interfaceClass) {
        return Constants.CALLBACK_SERVICE_PROXY_KEY + "." + System.identityHashCode(channel) + "." + interfaceClass + ".COUNT";
    }

    private static String getClientSideCallbackServiceCacheKey(int instid) {
        return Constants.CALLBACK_SERVICE_KEY + "." + instid;
    }


    private static byte isCallBack(URL url, String methodName, int argIndex) {
        // parameter callback rule: method-name.parameter-index(starting from 0).callback
        byte isCallback = CALLBACK_NONE;
        if(url != null) {
            String callback = url.getParameter(methodName + "." + argIndex + ".callback");
            if (callback != null) {
                if (callback.equalsIgnoreCase("true")) {
                    isCallback = CALLBACK_CREATE;
                }
                else if (callback.equalsIgnoreCase("false")) {
                    isCallback = CALLBACK_DESTROY;
                }
            }
        }
        return isCallback;
    }
}
