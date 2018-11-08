package com.sunso.lab.framework.rpc.ss.server;

import com.sunso.lab.framework.rpc.ss.common.RpcRequest;
import com.sunso.lab.framework.rpc.ss.common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Title:RpcHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午5:28
 * @m444@126.com
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private final Map<String, Object> interfaceMap;

    public RpcHandler(Map<String, Object> interfaceMap) {
        this.interfaceMap = interfaceMap;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {
        RpcResponse response = new RpcResponse();
        response.setRequestId(rpcRequest.getRequestId());
        try{
            response.setResult(this.handle(rpcRequest));
        }catch (Throwable e) {
            response.setError(e);
        }
        channelHandlerContext.writeAndFlush(response);
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();

        Object serviceBean = interfaceMap.get(className);
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        Class<?> cls = Class.forName(className);
        Method method = cls.getMethod(methodName, parameterTypes);
        return method.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
