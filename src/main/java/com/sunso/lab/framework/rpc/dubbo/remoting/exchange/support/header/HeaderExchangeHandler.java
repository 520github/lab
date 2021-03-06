package com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.header;

import com.sunso.lab.framework.rpc.dubbo.common.Constants;
import com.sunso.lab.framework.rpc.dubbo.common.URL;
import com.sunso.lab.framework.rpc.dubbo.common.util.NetUtils;
import com.sunso.lab.framework.rpc.dubbo.common.util.StringUtils;
import com.sunso.lab.framework.rpc.dubbo.remoting.Channel;
import com.sunso.lab.framework.rpc.dubbo.remoting.ChannelHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.ExecutionException;
import com.sunso.lab.framework.rpc.dubbo.remoting.RemotingException;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeChannel;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.ExchangeHandler;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Request;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.Response;
import com.sunso.lab.framework.rpc.dubbo.remoting.exchange.support.DefaultFuture;
import com.sunso.lab.framework.rpc.dubbo.remoting.transport.ChannelHandlerDelegate;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @Title:HeaderExchangeHandler
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2019/1/8上午11:44
 * @m444@126.com
 */
public class HeaderExchangeHandler implements ChannelHandlerDelegate {

    public static String KEY_READ_TIMESTAMP = HeartbeatHandler.KEY_READ_TIMESTAMP;

    public static String KEY_WRITE_TIMESTAMP = HeartbeatHandler.KEY_WRITE_TIMESTAMP;

    private final ExchangeHandler handler;

    public HeaderExchangeHandler(ExchangeHandler handler) {
        if (handler == null) {
            throw new IllegalArgumentException("handler == null");
        }
        this.handler = handler;
    }

    @Override
    public ChannelHandler getHandler() {
        if(handler instanceof ChannelHandlerDelegate) {
            return ((ChannelHandlerDelegate) handler).getHandler();
        }
        return handler;
    }

    @Override
    public void connected(Channel channel) throws RemotingException {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
        channel.setAttribute(KEY_WRITE_TIMESTAMP, System.currentTimeMillis());
        ExchangeChannel exchangeChannel = HeaderExchangeChannel.getOrAddChannel(channel);
        try{
            handler.connected(exchangeChannel);
        }finally {
            HeaderExchangeChannel.removeChannelIfDisconnected(channel);
        }
    }

    @Override
    public void disconnected(Channel channel) throws RemotingException {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
        channel.setAttribute(KEY_WRITE_TIMESTAMP, System.currentTimeMillis());

        ExchangeChannel exchangeChannel = HeaderExchangeChannel.getOrAddChannel(channel);
        try{
            handler.disconnected(exchangeChannel);
        }finally {
            DefaultFuture.closeChannel(channel);
            HeaderExchangeChannel.removeChannelIfDisconnected(channel);
        }
    }

    @Override
    public void sent(Channel channel, Object message) throws RemotingException {
        Throwable exception = null;
        try{
            channel.setAttribute(KEY_WRITE_TIMESTAMP, System.currentTimeMillis());
            ExchangeChannel exchangeChannel = HeaderExchangeChannel.getOrAddChannel(channel);
            try{
                handler.sent(exchangeChannel, message);
            }finally {
                HeaderExchangeChannel.removeChannelIfDisconnected(channel);
            }
        }catch (Throwable t) {
            exception = t;
        }

        if(message instanceof Request) {
            Request request = (Request) message;
            DefaultFuture.sent(channel, request);
        }

        if(exception != null) {
            if (exception instanceof RuntimeException) {
                throw (RuntimeException) exception;
            }
            else if(exception instanceof RemotingException) {
                throw (RemotingException) exception;
            }
            else {
                throw new RemotingException(channel.getLocalAddress(), channel.getRemoteAddress(),
                        exception.getMessage(), exception);
            }
        }
    }

    @Override
    public void received(Channel channel, Object message) throws RemotingException {
        channel.setAttribute(KEY_READ_TIMESTAMP, System.currentTimeMillis());
        final ExchangeChannel exchangeChannel = HeaderExchangeChannel.getOrAddChannel(channel);
        try{
            if(message instanceof Request) {
                // handle request.
                Request request = (Request) message;
                if(request.isEvent()) {
                    handlerEvent(channel, request);
                }
                else if(request.isTwoWay()){
                    handleRequest(exchangeChannel, request);
                }
                else {
                    handler.received(exchangeChannel, request.getData());
                }
            }
            else if(message instanceof Response) {
                handleResponse(channel, (Response) message);
            }
            else if(message instanceof String) {
                if(isClientSide(channel)) {
                    Exception e = new Exception("Dubbo client can not supported string message: " + message + " in channel: " + channel + ", url: " + channel.getUrl());
                    //logger.error(e.getMessage(), e);
                }
                else {
                    String echo = handler.telnet(channel, (String) message);
                    if (echo != null && echo.length() > 0) {
                        channel.send(echo);
                    }
                }
            }
            else {
                handler.received(exchangeChannel, message);
            }
        }finally {
            HeaderExchangeChannel.removeChannelIfDisconnected(channel);
        }
    }

    @Override
    public void caught(Channel channel, Throwable exception) throws RemotingException {
        if(exception instanceof ExecutionException) {
            ExecutionException e = (ExecutionException) exception;
            Object msg = e.getRequest();
            if(msg instanceof Request) {
                Request req = (Request) msg;
                if(req.isTwoWay() && !req.isHeartbeat()) {
                    Response res = new Response(req.getId(), req.getVersion());
                    res.setStatus(Response.SERVER_ERROR);
                    res.setErrorMessage(StringUtils.toString(e));
                    channel.send(res);
                    return;
                }
            }
        }

        ExchangeChannel exchangeChannel = HeaderExchangeChannel.getOrAddChannel(channel);
        try{
            handler.caught(exchangeChannel, exception);
        }finally {
            HeaderExchangeChannel.removeChannelIfDisconnected(channel);
        }
    }

    void handleRequest(final ExchangeChannel channel, Request req) throws RemotingException {
        Response res = new Response(req.getId(), req.getVersion());
        if(req.isBroken()) {
            Object data = req.getData();
            String msg;
            if (data == null) {
                msg = null;
            } else if (data instanceof Throwable) {
                msg = StringUtils.toString((Throwable) data);
            } else {
                msg = data.toString();
            }
            res.setErrorMessage("Fail to decode request due to: " + msg);
            res.setStatus(Response.BAD_REQUEST);

            channel.send(res);
            return;
        }

        // find handler by message class.
        Object msg = req.getData();
        try{
            CompletableFuture<Object> future = handler.reply(channel, msg);
            if(future.isDone()) {
                res.setStatus(Response.OK);
                res.setResult(future.get());
                channel.send(res);
                return;
            }

            future.whenComplete((result, t)-> {
                try{
                    if(t == null) {
                        res.setStatus(Response.OK);
                        res.setResult(result);
                    }
                    else {
                        res.setStatus(Response.SERVICE_ERROR);
                        res.setErrorMessage(StringUtils.toString(t));
                    }
                    channel.send(res);
                }catch (Exception e) {
                    //logger.warn("Send result to consumer failed, channel is " + channel + ", msg is " + e);
                }finally {
                    //HeaderExchangeChannel.removeChannelIfDisconnected(channel);
                }
            });
        }catch (Throwable t) {
            res.setStatus(Response.SERVICE_ERROR);
            res.setErrorMessage(StringUtils.toString(t));
            channel.send(res);
        }
    }

    static void handleResponse(Channel channel, Response response) throws RemotingException {
        if (response != null && !response.isHeartbeat()) {
            DefaultFuture.received(channel, response);
        }
    }

    void handlerEvent(Channel channel, Request req) throws RemotingException {
        if (req.getData() != null && req.getData().equals(Request.READONLY_EVENT)) {
            channel.setAttribute(Constants.CHANNEL_ATTRIBUTE_READONLY_KEY, Boolean.TRUE);
        }
    }

    private static boolean isClientSide(Channel channel) {
        InetSocketAddress address = channel.getRemoteAddress();
        URL url = channel.getUrl();
        return url.getPort() == address.getPort() &&
                NetUtils.filterLocalHost(url.getIp())
                        .equals(NetUtils.filterLocalHost(address.getAddress().getHostAddress()));
    }
}
