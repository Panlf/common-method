package com.plf.netty.rpc.provider.server;

import com.plf.netty.rpc.provider.HelloServiceImpl;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//获取客户端发送的消息，并调用服务
		System.out.println("msg="+msg);
		if(msg.toString().startsWith("HelloService#hello#")) {
			String result = new HelloServiceImpl().hello(
					msg.toString().substring(msg.toString().lastIndexOf("#")+1));
			ctx.writeAndFlush(result);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		ctx.close();
	}
}
