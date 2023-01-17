package com.plf.netty.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TcpServer {

    private Bootstrap bootstrap;

    private NioEventLoopGroup bossgroup;
    private NioEventLoopGroup workgroup;

    public void init() throws InterruptedException {
        this.bossgroup = new NioEventLoopGroup();
        this.workgroup = new NioEventLoopGroup();
        ServerBootstrap server = new ServerBootstrap();
        this.bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(bossgroup);
        server.group(bossgroup, workgroup);
        server.channel(NioServerSocketChannel.class)
                //处理打印日志
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //服务端channel，将服务端的数据发送给客户端，所以构造函数参数要传入客户端的channel
                        ch.pipeline().addLast("serverHandler", new DataHandler(getClientChannel(ch)));
                    }
                }).option(ChannelOption.SO_BACKLOG, 1024)
                // SO_SNDBUF发送缓冲区，SO_RCVBUF接收缓冲区，SO_KEEPALIVE开启心跳监测（保证连接有效）
                .option(ChannelOption.SO_SNDBUF, 16 * 1024)
                .option(ChannelOption.SO_RCVBUF, 16 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true);
        server.bind(9999).channel().closeFuture().sync();
    }

    private Channel getClientChannel(SocketChannel ch) throws InterruptedException {
        this.bootstrap
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        //客户端端channel，客户端返回的数据给服务端，所以构造函数参数要传入服务端的channel
                        socketChannel.pipeline().addLast("clientHandler", new DataHandler(ch));
                    }
                });
        ChannelFuture sync = bootstrap.connect("127.0.0.1", 3306).sync();
        return sync.channel();
    }


    public static void main(String[] args) throws InterruptedException {
        TcpServer tcpServer = new TcpServer();
        tcpServer.init();
    }
}