package com.plf.netty.chat.group;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {
	private int port;
	
	public GroupChatServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
		ServerBootstrap b = new ServerBootstrap();
		
		b.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//获取到pipeline
					ChannelPipeline pipeline = ch.pipeline();
					//加入解码器
					pipeline.addLast("decoder",new StringDecoder());
					//加入编码器
					pipeline.addLast("encoder",new StringEncoder());
					//加入自己的业务处理handler
					pipeline.addLast(new GroupChatServerHandler());
				}
			
			});
			System.out.println("netty 服务器启动");
	
			ChannelFuture channelFuture = b.bind(port).sync();
			
			//监听关闭
			channelFuture.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GroupChatServer(7000).run();
	}

}
