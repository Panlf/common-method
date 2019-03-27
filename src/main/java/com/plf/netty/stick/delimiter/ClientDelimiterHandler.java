package com.plf.netty.stick.delimiter;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientDelimiterHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try{
			
			String message = msg.toString();
			System.out.println("from server : "+message);
		}finally {
			//用于释放缓存。避免内存溢出
			ReferenceCountUtil.release(msg);
		}
		
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
