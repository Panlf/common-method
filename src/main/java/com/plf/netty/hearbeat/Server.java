package com.plf.netty.hearbeat;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

public class Server {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup,workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.handler(new LoggingHandler(LogLevel.INFO));
			b.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					/**
					 * IdleStateHandler 是netty提供的处理空闲状态的处理器
					 * readerIdleTimeSeconds	表示多长时间没有读，就会发送一个心跳检测包检测是否连接
					 * writerIdleTimeSeconds    表示多长时间没有读，就会发送一个心跳检测包检测是否连接
					 * allIdleTimeSeconds      表示多长时间没有读写，就会发送一个心跳检测包检测是否连接
					 */
					pipeline.addLast(new IdleStateHandler(3, 4, 5,TimeUnit.SECONDS));
					pipeline.addLast(new ServerHandler());
					
				}
			});
			
			ChannelFuture channelFuture = b.bind(9000).sync();
			
			//监听关闭
			channelFuture.channel().closeFuture().sync();
			
		}finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
