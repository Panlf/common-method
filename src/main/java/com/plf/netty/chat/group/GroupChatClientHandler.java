package com.plf.netty.chat.group;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class GroupChatClientHandler extends ChannelHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		 System.out.println(msg);
	}
}
