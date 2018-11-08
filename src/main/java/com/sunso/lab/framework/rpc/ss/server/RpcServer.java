package com.sunso.lab.framework.rpc.ss.server;

import com.sunso.lab.framework.rpc.ss.common.RpcRequest;
import com.sunso.lab.framework.rpc.ss.common.RpcResponse;
import com.sunso.lab.framework.rpc.ss.protocol.RpcDecoder;
import com.sunso.lab.framework.rpc.ss.protocol.RpcEncoder;
import com.sunso.lab.framework.rpc.ss.registry.ServiceRegistry;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title:RpcServer
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午4:45
 * @m444@126.com
 */
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private ServiceRegistry serviceRegistry;
    private String serverAddress;
    private Map<String, Object> interfaceMap = new HashMap<String, Object>();

    public RpcServer(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RpcServer(String serverAddress, ServiceRegistry serviceRegistry) {
        this.serverAddress = serverAddress;
        this.serviceRegistry = serviceRegistry;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if(MapUtils.isEmpty(serviceBeanMap)) {
            return;
        }
        for(Object serviceBean: serviceBeanMap.values()) {
            String interfaceName = serviceBean.getClass().getAnnotation(RpcService.class).value().getName();
            interfaceMap.put(interfaceName, serviceBean);
        }
    }

    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline()
                                    .addLast(new RpcDecoder(RpcRequest.class))
                                    .addLast(new RpcEncoder(RpcResponse.class))
                                    .addLast(new RpcHandler(interfaceMap))
                            ;
                        }
                    }).option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
            ;

            String addrArray[] = serverAddress.split(":");
            String host = addrArray[0];
            int port = Integer.parseInt(addrArray[1]);

            if(serviceRegistry != null) {
                serviceRegistry.register(serverAddress);
            }
            ChannelFuture future = bootstrap.bind(host, port).sync();
            future.channel().closeFuture().sync();

        }finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
