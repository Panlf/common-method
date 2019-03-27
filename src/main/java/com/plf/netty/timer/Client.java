package com.plf.netty.timer;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.WriteTimeoutHandler;

/**
 * 因为客户端是请求的发起者，不需要监听
 * 	只需要定义唯一的一个线程组即可
 * */
public class Client {
	
	//处理请求和处理服务端响应的线程组
	private EventLoopGroup group = null;

	//客户启动相关配置信息
	private Bootstrap bootstrap = null;
	
	private ChannelFuture future = null;
	
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
		try {
			setHandlers();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setHandlers() throws InterruptedException{
		/**
		 * 客户端的Bootstrap没有childHandler方法，只有handler方法
		 * 方法等同于ServerBootstrap中的childHandler
		 * 在客户端必须绑定处理器，也就是调用handler方法
		 * 服务器必须绑定处理器，必须调用childHandler方法
		 */
		this.bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			protected void initChannel(SocketChannel ch) throws Exception {	
				/**
				 * 断线重连机制：
				 * 	客户端数量多，且需要传递的数据量级较大，可以周期性的发送数据的时候使用。要求对数据的即时性不高的时候才可以使用
				 * 
				 * 	可以使用数据缓存。不是每条数据进行一次数据交互。可以定时回收资源，对资源利用率高。相对而言，即时性可以通过其他方式保证。
				 */
				ch.pipeline().addLast(new WriteTimeoutHandler(3));
				ch.pipeline().addLast(new ClientTimerHandler());
			}
		});
	}
	
	/*
	 * shutdownGracefully 方法是一个安全关闭的方法。可以保证不放弃任何一个已接收的客户端请求
	 */
	public void release(){
		this.group.shutdownGracefully();
	}
	
	public ChannelFuture getChannelFuture(String host,int port) throws InterruptedException{
		if(future == null){
			future = this.bootstrap.connect(host, port).sync();
		}
		if(!future.channel().isActive()){
			future = this.bootstrap.connect(host, port).sync();
		}
		return future;
	}
	
	public static void main(String[] args) {
		Client client = null;
		ChannelFuture future = null;
		Scanner scanner =null;
		try {
			client = new Client();
			future = client.getChannelFuture("localhost", 9999);
			
			
			future.channel().writeAndFlush(Unpooled.copiedBuffer("hello".getBytes("UTF-8")));
			TimeUnit.SECONDS.sleep(5);
			
			future = client.getChannelFuture("localhost", 9999);
			future.channel().writeAndFlush(Unpooled.copiedBuffer("hi".getBytes("UTF-8")));
			
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

