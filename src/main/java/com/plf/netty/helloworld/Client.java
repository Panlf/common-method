package com.plf.netty.helloworld;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 因为客户端是请求的发起者，不需要监听
 * 	只需要定义唯一的一个线程组即可
 * */
public class Client {
	
	//处理请求和处理服务端响应的线程组
	private EventLoopGroup group = null;

	//客户启动相关配置信息
	private Bootstrap bootstrap = null;
	
	public Client(){
		init();
	}
	
	private void init() {
		group = new NioEventLoopGroup();
		bootstrap= new Bootstrap();
		//绑定线程组
		bootstrap.group(group);
		//设定通讯模式为NIO，同步非阻塞
		bootstrap.channel(NioSocketChannel.class);
	}

	public ChannelFuture doRequest(String host,int port,final ChannelHandler...handlers) throws InterruptedException{
		/**
		 * 客户端的Bootstrap没有childHandler方法，只有handler方法
		 * 方法等同于ServerBootstrap中的childHandler
		 * 在客户端必须绑定处理器，也就是调用handler方法
		 * 服务器必须绑定处理器，必须调用childHandler方法
		 */
		this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel ch) throws Exception {		
				ch.pipeline().addLast(handlers);
			}
		});
		
		ChannelFuture future = this.bootstrap.connect(host,port).sync();
		return future;
	}
	
	/*
	 * shutdownGracefully 方法是一个安全关闭的方法。可以保证不放弃任何一个已接收的客户端请求
	 */
	public void release(){
		this.group.shutdownGracefully();
	}
	
	public static void main(String[] args) {
		Client client = null;
		ChannelFuture future = null;
		Scanner scanner =null;
		try {
			client = new Client();
			future = client.doRequest("localhost", 9999, new ClientHandler());
			
			while(true){
				scanner = new Scanner(System.in);
				System.out.println("enter message send to server (enter 'exit' for close client) > ");
				String line = scanner.nextLine();
				if("exit".equals(line)){
					//addListener 增加监听，当某条件满足的时，触发监听器
					//ChannelFutureListener.CLOSE 关闭监听器，当ChannelFuture返回执行后，关闭连接。
					future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")))
						.addListener(ChannelFutureListener.CLOSE);
					break;
				}
				future.channel().writeAndFlush(Unpooled.copiedBuffer(line.getBytes("UTF-8")));
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != scanner){
				scanner.close();
			}
			if(null != future){
				try {
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(null != client){
				client.release();
			}
		}
	}
}

