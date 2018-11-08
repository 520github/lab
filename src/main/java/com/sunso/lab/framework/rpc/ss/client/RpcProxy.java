package com.sunso.lab.framework.rpc.ss.client;

import com.sunso.lab.framework.rpc.ss.common.RpcRequest;
import com.sunso.lab.framework.rpc.ss.common.RpcResponse;
import com.sunso.lab.framework.rpc.ss.registry.ServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Title:RpcProxy
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午9:43
 * @m444@126.com
 */
public class RpcProxy {
    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RpcProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T create(Class<?> interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setRequestId(UUID.randomUUID().toString());

                request.setClassName(method.getDeclaringClass().getName());
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);

                if(serviceDiscovery != null) {
                    serverAddress = serviceDiscovery.discover();
                }

                String[] array = serverAddress.split(":");
                String host = array[0];
                int port = Integer.parseInt(array[1]);

                RpcClient client = new RpcClient(host, port);
                RpcResponse response = client.send(request);

                if(response.isError()) {
                    throw response.getError();
                }
                return response.getResult();
            }
        });
    }
}
