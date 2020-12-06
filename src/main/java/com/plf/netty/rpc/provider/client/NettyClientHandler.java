package com.plf.netty.rpc.provider.client;

import java.util.concurrent.Callable;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class NettyClientHandler extends ChannelHandlerAdapter implements Callable<Object>{

	private ChannelHandlerContext context; //上下文
	
	private String result;//返回的结果
	
	private String param;//客户端调用的方法，传入的参数
	
	
	//与服务器的连接创建后，就会被调用  这个方法是第一个被调用(1)
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		this.context = ctx ;
	}
	
	//收到服务器的数据后，调用方法 (4)
	@Override
	public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		result = msg.toString();
		
		notify();//唤醒等待的线程
	}
	
	//被代理对象调用，发送数据给服务器，-> wait -> 等待被唤醒(channelRead) -> 返回结果 (3)==>(5)
	@Override
	public synchronized Object call() throws Exception {
		
		context.writeAndFlush(param);
		
		//进行wait
		wait(); // 等待channelRead方法获取到服务器的结果后，唤醒
		
		return result;
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
	//(2)设参数
	void setParam(String param) {
		this.param = param;
	}

}
