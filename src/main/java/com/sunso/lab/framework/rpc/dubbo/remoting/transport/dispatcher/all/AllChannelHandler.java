package com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher.all;

import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.ExecutionException;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher.ChannelEventRunnable;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher.ChannelEventRunnable.ChannelState;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.dispatcher.WrappedChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Response;
import com.sunso.lab.framework.rpc.dubbo.common.URL;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;

/**
 * @Title:AllChannelHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/2下午3:52
 * @m444@126.com
 */
public class AllChannelHandler extends WrappedChannelHandler {

    public AllChannelHandler(ChannelHandler handler, URL url) {
        super(handler, url);
    }

    public void connected(Channel channel) throws RemotingException {
        ExecutorService executorService = getExecutorService();
        try{
            executorService.execute(new ChannelEventRunnable(channel, handler, ChannelState.CONNECTED));
        }catch (Throwable t) {
            throw new ExecutionException("connect event", channel, getClass() + " error when process connected event .", t);
        }
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        ExecutorService cexecutor = getExecutorService();
        try {
            cexecutor.execute(new ChannelEventRunnable(channel, handler, ChannelState.DISCONNECTED));
        } catch (Throwable t) {
            throw new ExecutionException("disconnect event", channel, getClass() + " error when process disconnected event .", t);
        }
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        ExecutorService cexecutor = getExecutorService();
        try {
            cexecutor.execute(new ChannelEventRunnable(channel, handler, ChannelState.CAUGHT, exception));
        } catch (Throwable t) {
            throw new ExecutionException("caught event", channel, getClass() + " error when process caught event .", t);
        }
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        ExecutorService cexecutor = getExecutorService();
        try{
            cexecutor.execute(new ChannelEventRunnable(channel, handler, ChannelState.RECEIVED, message));
        } catch (Throwable t) {
            if(message instanceof Request && t instanceof RejectedExecutionException){
                Request request = (Request)message;
                if(request.isTwoWay()) {
                    handleThreadPoolFull(channel, request, t);
                    return;
                }
            }
            throw new ExecutionException(message, channel, getClass() + " error when process received event .", t);
        }
    }

    private void handleThreadPoolFull(Channel channel, Request request, Throwable t) {
        String msg = "Server side(" + url.getIp() + "," + url.getPort() + ") threadpool is exhausted ,detail msg:" + t.getMessage();
        Response response = new Response(request.getId(), request.getVersion());
        response.setStatus(Response.SERVER_THREADPOOL_EXHAUSTED_ERROR);
        response.setErrorMessage(msg);
        channel.send(response);
    }

}
