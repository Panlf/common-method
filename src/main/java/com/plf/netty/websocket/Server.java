package com.plf.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

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
					//因为基于http协议，使用http的编码和解码器
					pipeline.addLast(new HttpServerCodec());
					//是以块方式写，添加ChunkedWriteHandler处理器
					pipeline.addLast(new ChunkedWriteHandler());
					
					/**
					 * 1、http数据在传输过程中分段，HttpObjectAggregator，就是可以将多个段聚合
					 * 2、当浏览器发送大量数据时，就会发出多次http请求
					 */
					pipeline.addLast(new HttpObjectAggregator(8192));					
				
					/**
					 * 1、对应websocket，他的数据是以帧(frame)形式传递
					 * 2、可以看到WebSocketFrame 下面有六个子类
					 * 3、浏览器请求时 ws://localhost:9000/hello表示请求的uri
					 * 4、WebSocketServerProtocolHandler核心功能是将http协议升级为ws协议，保持长连接
					 * 5、是通过一个 状态码 101
					 */
					pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));					
					
					pipeline.addLast(new TextWebSocketFrameHandler());
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
