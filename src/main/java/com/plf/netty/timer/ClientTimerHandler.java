package com.plf.netty.timer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientTimerHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try{
			//获取读取的数据，是一个缓冲
			ByteBuf readBuffer = (ByteBuf) msg;
			//创建一个字节数组，用于保存缓存中的数据
			byte[] tempDatas = new byte[readBuffer.readableBytes()];
			//将缓存中的数据读取到字节数组中
			readBuffer.readBytes(tempDatas);
			String message = new String(tempDatas,"UTF-8");
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
	
	/**
	 * 当连接建立成功后，触发的逻辑
	 * 在一次连接中只运行一次
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client channel active");
	}
	
}
