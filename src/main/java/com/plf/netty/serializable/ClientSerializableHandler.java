package com.plf.netty.serializable;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientSerializableHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("from server : ClassName - "+msg.getClass().getName()+
				" ;message :"+msg.toString());
		
	}
	
	/**
	 * 异常处理逻辑，当客户端异常退出时，也会运行
	 * 	ChannelHandlerContext关闭，也代表当前客户端连接的资源关闭
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exceptionCaught method run...");
		ctx.close();
	}
}
