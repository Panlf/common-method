package com.plf.netty.websocket;

import org.joda.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 
 * @author Panlf
 *
 */
//这里TextWebSocketFrame类型，表示一个文本帧(frame)
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		System.out.println("服务器收到消息:"+msg.text());
		
		ctx.channel().writeAndFlush(
				new TextWebSocketFrame("服务器时间"+
		LocalDateTime.now()+" "+
		msg.text()));
	}
	
	// web客户端连接后，触发方法
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		//id 表示唯一的值  LongText 是唯一的  ShortText 不是唯一 
		System.out.println("handlerAdded 被调用"+ctx.channel().id().asLongText());
		System.out.println("handlerAdded 被调用"+ctx.channel().id().asShortText());
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		System.out.println("handlerRemoved 被调用"+ctx.channel().id().asLongText());	
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("异常发生"+cause.getMessage());
		ctx.close();
	}
}
