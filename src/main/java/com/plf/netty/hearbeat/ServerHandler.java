package com.plf.netty.hearbeat;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

public class ServerHandler extends ChannelHandlerAdapter {
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			String eventType = null;
			switch (event.state()) {
			case READER_IDLE:
				eventType = "读空闲";
				break;
			case WRITER_IDLE:
				eventType = "写空闲";
				break;
			case ALL_IDLE:
				eventType = "读写空闲";
				break;
			}
			System.out.println(ctx.channel().remoteAddress() + "---超时时间---" + eventType);
			System.out.println("服务器相应处理。。。");
		}
	}
}
