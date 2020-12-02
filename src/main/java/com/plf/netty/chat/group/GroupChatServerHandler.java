package com.plf.netty.chat.group;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class GroupChatServerHandler extends ChannelHandlerAdapter {

	//定义一个channel组，管理所有的channel
	//GlobalEventExecutor.INSTANCE是全局的事件执行器，是一个单例
	private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	//handlerAdded 表示连接建立，一旦连接，第一个被执行
	//将当前channel加入到channelGroup
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		//将该客户加入聊天的信息推送给其他在线的客户端
		
		/**
		 * 该方法会将channelGroup中所有的channel遍历，并发送消息
		 * 不需要自己遍历
		 */
		channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天 "+dateFormat.format(new Date())+"\n");
		channelGroup.add(channel);
	}
	
	//表示channel处于活动状态
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress()+" 上线了~");
	}
	
	//表示channel处于不活动状态
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().remoteAddress()+" 离线了~");
	}
	
	//断开连接
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+" 离开了\n");
		//channelGroup.remove(channel);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel channel = ctx.channel();
		
		channelGroup.forEach(ch->{
			if(channel != ch) {
				ch.writeAndFlush("[客户]"+channel.remoteAddress()+" 发送了消息"+msg+"\n");
			}else {
				ch.writeAndFlush("[自己]发送了消息"+msg+"\n");
			}
		});
		
		
	}
	
	/**
	 * 异常处理逻辑，当客户端异常退出时，也会运行
	 * 	ChannelHandlerContext关闭，也代表当前客户端连接的资源关闭
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("server exceptionCaught method run...");
		ctx.close();
	}

}
