package com.plf.netty.chat.group;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatClinet {

	private final String host;
	private final int port;

	public GroupChatClinet(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void run() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Scanner scanner = new Scanner(System.in);
		try {
			Bootstrap bootstrap = new Bootstrap();

			bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// 获取到pipeline
					ChannelPipeline pipeline = ch.pipeline();
					// 加入解码器
					pipeline.addLast("decoder", new StringDecoder());
					// 加入编码器
					pipeline.addLast("encoder", new StringEncoder());
					pipeline.addLast(new GroupChatClientHandler());
				}
			});
			ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
			Channel channel = channelFuture.channel();
			System.out.println("------" + channel.localAddress() + "------");

			while (scanner.hasNextLine()) {
				String msg = scanner.nextLine();
				channel.writeAndFlush(msg + "\r\n");
			}
		} finally {
			group.shutdownGracefully();
			scanner.close();
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GroupChatClinet("127.0.0.1",7000).run();
	}

}
