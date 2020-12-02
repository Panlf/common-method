package com.plf.netty.timer;

/***
 * netty中支持单线程模型，多线程模型，主从多线程模型
 * 	1、单线程模型
 * 		在ServerBootstrap调用方法group的时候，传递的参数是一个线程组，且在构造线程组的时候，构造参数为1，这种模式即为单线程模型。
 * 		
 * 		个人机开发测试使用。不推荐。
 * 		
 * 
 * 	2、多线程模型
 * 		在ServerBootstrap调用方法group的时候，传递的参数是两个不同的线程组。负责监听的acceptor线程组，线程数为1，也就是构造参数为1。
 * 		负责处理客户端任务的线程组，线程数大于1，也就是 构造参数大于1,。这种模式即为多线程模型。
 * 
 * 		长连接，且客户端数量较少，连接持续时间较长情况下使用。如：企业内部交流使用。
 * 
 *  3、主从多线程模型
 * 		在ServerBootstrap调用方法group的时候，传递的参数是两个不同的线程组。负责监听的acceptor线程组，线程数大于1，也就是构造参数大于1。
 * 		负责处理客户端任务的线程组，线程数大于1，也就是 构造参数大于1,。这种模式即为主从多线程模型。
 * 
 * 		长连接，且客户端数量相对较多，连接持续时间较长情况下使用。如：对外提供服务的相册服务器。
 * 		
 * 
 */
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class Server {
	//监听线程组，监听客户端请求
	private EventLoopGroup acceptorGroup = null;
	//处理客户端相关操作线程组，负责处理与客户端的数据通讯
	private EventLoopGroup clientGroup = null;
	//服务启动相关配置信息
	private ServerBootstrap bootstrap = null;
	
	public Server(){
		init();
	}
	
	private void init() {
		//初始化线程组，构建线程组的时候如果不传递参数，则默认构建的线程组线程数是CPU核心数量
		acceptorGroup = new NioEventLoopGroup();
		clientGroup = new NioEventLoopGroup();
		//初始化服务的配置
		bootstrap = new ServerBootstrap();
		//绑定线程组
		bootstrap.group(acceptorGroup,clientGroup)
		//设定通讯模式为NIO，同步非阻塞
		.channel(NioServerSocketChannel.class)
		//设定缓冲区的大小 缓存区的单位是字节
		.option(ChannelOption.SO_BACKLOG, 1024)
		//SO_SNDBUF 发送缓冲区 SO_RCVBUF 接受缓冲区  SO_KEEPALIVE 开启心跳监测(保证连接有效)
		.option(ChannelOption.SO_SNDBUF, 16*1024)
				.option(ChannelOption.SO_RCVBUF, 16*1024);
				//.option(ChannelOption.SO_KEEPALIVE, true);
				
	}

	/**
	 * 监听处理逻辑
	 * @param port 端口
	 * @param acceptHandlers 处理器，如何处理客户端请求
	 * @return
	 * @throws InterruptedException
	 */
	public ChannelFuture doAccept(int port) throws InterruptedException{
		/*
		 * childHandler 是服务的Bootstrap独有的方法。用于提供处理对象的。
		 * 	可以一次性增加若干个处理逻辑。是类似责任链模式的处理方式。
		 * 	增加A，B两个处理逻辑，在处理客户端	请求数据的时候，根据A -> B顺序依次处理
		 *
		 *	ChannelInitializer - 用于提供处理器的一个模型对象
		 *	 其中定义了一个方法，initChannel方法。
		 *		方法是用于初始化处理逻辑责任链条的。
		 *		可以保证服务端的Bootstrap只初始化一次处理器，尽量提供处理逻辑的重用。
		 *		避免反复的创建处理器对象。节约资源开销。
		 *
		 */
		bootstrap.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {	
				//定义一个定时断线处理器，当多长时间内，没有任何的可读取数据，自动断开连接
				//还有一个写操作WriteTimeoutHandler
				//构造参数，就是间隔时长，默认单位是秒
				//自定义间隔时长单位。new ReadTimeoutHandler(long times,TimeUnit unit)
				ch.pipeline().addLast(new ReadTimeoutHandler(3));
				
				ch.pipeline().addLast(new ServerTimerHandler());
			}
		});
		//bind 方法 - 绑定监听端口。ServerBootstrap 可以绑定多个监听端口。多次调用bind方法即可
		//sync - 开始监听逻辑。返回一个ChannelFuture。返回结果代表的是监听成功后的一个对应的未来结果
		// 可以使用ChannelFuture实现后续的服务器和客户端的交互
		ChannelFuture future = bootstrap.bind(port).sync();
		return future;
	}
	
	/*
	 * shutdownGracefully 方法是一个安全关闭的方法。可以保证不放弃任何一个已接收的客户端请求
	 */
	public void release(){
		this.acceptorGroup.shutdownGracefully();
		this.clientGroup.shutdownGracefully();
	}
	
	public static void main(String[] args) {
		ChannelFuture future = null;
		Server server = null;
		
		try{
			server = new Server();
			future = server.doAccept(9999);
			System.out.println("server started ... ");
			
			future.channel().closeFuture().sync();
			
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(null != future){
				try {
					future.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(null != server){
				server.release();
			}
		}
		
	}

}
