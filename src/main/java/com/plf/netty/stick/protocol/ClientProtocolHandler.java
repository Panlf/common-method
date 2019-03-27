package com.plf.netty.stick.protocol;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class ClientProtocolHandler extends ChannelHandlerAdapter {
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		try{
			String message = msg.toString();
			System.out.println("client receive protocol content : "+message);
			message = ProtocolParser.parse(message);
			if(null == message){
				System.out.println("error request from server");
				return;
			}
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
