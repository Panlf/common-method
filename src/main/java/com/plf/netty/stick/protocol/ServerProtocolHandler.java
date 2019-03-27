package com.plf.netty.stick.protocol;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelHandler.Sharable;

/**
 * 
 * @Sharable 代表当亲Handler是一个可以分享的处理器。也就意味着，服务器注册此Handler后，可以分享多个客户端使用
 * 	如果不使用注解描述类型，则客户端请求时，必须为客户端重新创建一个新Handler对象
 * 
 * 如果handler是Sharable的，一定要避免定义可写的实例变量
 * */
//@Sharable
public class ServerProtocolHandler extends ChannelHandlerAdapter {
	
	/**
	 * 业务处理逻辑
	 * 	用于处理读取数据的请求逻辑
	 * @param ctx  上下文对象。其中包含于客户端建立连接的所有资源。如：对应的Channel
	 * @param msg  读取到的数据，默认的类型是ByteBuf，是netty自定义的，是对ByteBuffer的封装，不需要考虑复位问题
	 * @throws Exception
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String message = msg.toString();
		System.out.println("server receive protocol content : "+message);
		message = ProtocolParser.parse(message);
		
		if(null == message){
			System.out.println("error request from client");
			return;
		}
		
		System.out.println("from client : "+message);
	
		String line = "server message";
		
		line = ProtocolParser.transferTo(line);
		
		System.out.println("server send protocol content : "+line);
			
		
		//写操作自动释放缓存，避免内存溢出问题
		ctx.writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
		//注意，如果调用的是write方法，不会刷新缓存，缓存中的数据不会发送到客户端，必须再次调用flush才行。
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

	static class ProtocolParser{
		public static String parse(String message){
			String[] temp = message.split("HEADBODY");
			temp[0] = temp[0].substring(4);
			temp[1] = temp[1].substring(0, (temp[1].length()-4));
			int length = Integer.parseInt(temp[0].substring(temp[0].indexOf(":")+1));
			if(length != temp[1].length()){
				return null;
			}
			return temp[1];
		}
		
		public static String transferTo(String message){
			message = "HEADcontent-length:"+message.length()+"HEADBODY"+message+"BODY";
			return message;
		}
	}
}
