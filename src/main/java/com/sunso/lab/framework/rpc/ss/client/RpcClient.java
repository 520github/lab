package com.sunso.lab.framework.rpc.ss.client;

import com.sunso.lab.framework.rpc.ss.common.RpcRequest;
import com.sunso.lab.framework.rpc.ss.common.RpcResponse;
import com.sunso.lab.framework.rpc.ss.protocol.RpcDecoder;
import com.sunso.lab.framework.rpc.ss.protocol.RpcEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Title:RpcClient
 * @Copyright: Copyright (c) 2018
 * @Description: <br>
 * @Company: sunso-fintech
 * @Created on 2018/11/8下午5:57
 * @m444@126.com
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {
    private String host;
    private int port;
    private RpcResponse response;
    private final Object obj = new Object();

    public RpcClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public RpcResponse send(final RpcRequest request) throws Throwable {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //向pipeline中添加编码、解码、业务处理的handler
                    socketChannel.pipeline()
                            .addLast(new RpcEncoder(RpcRequest.class))
                            .addLast(new RpcDecoder(RpcResponse.class))
                            .addLast(this);
                }
            }).option(ChannelOption.SO_KEEPALIVE, true);

            //连接服务器
            ChannelFuture future = bootstrap.connect(host, port).sync();

            //将request对象写入outbundle处理后发出（即RpcEncoder编码器）
            future.channel().writeAndFlush(request).sync();


            //用线程等待的方式决定是否关闭连接
            //其意义是：先在此阻塞，等待获取到服务端的返回后，被唤醒，从而关闭网络连接
            synchronized (obj) {
                obj.wait();
            }

            if(response != null) {
                future.channel().closeFuture().sync();
            }
            return response;
        }finally {
            group.shutdownGracefully();
        }
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {
        this.response = response;
        synchronized (obj) {
            obj.notifyAll();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
