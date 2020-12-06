package com.plf.netty.rpc.provider.client;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {

	private static ExecutorService executor = Executors.
			newFixedThreadPool(Runtime.getRuntime().availableProcessors());


	private static NettyClientHandler clientHandler;
	
	//编写方法使用代理模式，获取一个代理对象
	public Object getBean(final Class<?> serviceClass,final String providerName) {
		return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
				new Class<?>[] {serviceClass}, (proxy,method,args)->{
					//{} 部分的代码，客户端每调用一次hello，就会进入到该代码
					if(clientHandler == null) {
						initClient();
					}
					
					//设置要发送给服务器端的信息
					//providerName 协议头 args[0] 就是客户端调用api hello(????) 参数
					clientHandler.setParam(providerName+args[0]);
					
					return executor.submit(clientHandler).get();
				});
	}
	
	//初始化客户端
	private static void initClient() {
		clientHandler = new NettyClientHandler();
		NioEventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap= new Bootstrap();
		
		bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY,true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast(new StringDecoder());
					pipeline.addLast(new StringEncoder());
					pipeline.addLast(clientHandler);
				}
			});
		try {
			bootstrap.connect("127.0.0.1",8080).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
