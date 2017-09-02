package com.plf.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOSelectorServer {

	private Selector selector;
	private int keys=0;
	private ServerSocketChannel serverChannel=null;
	
	//初始化服务端连接通道和管理器，已经注册事件
	public void initServer() throws IOException{
		this.selector = Selector.open();
		serverChannel = ServerSocketChannel.open();
		serverChannel.socket().bind(new InetSocketAddress("127.0.0.1",8888));
		serverChannel.configureBlocking(false);
		
		//把serverChannel这个通道注册到通道服务器对象acceptSelector去，当有客户端连接时触发
		serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
	}
	//对客户端的请求（通道上面感兴趣的事件）进行监听
	public void listen() throws IOException{
		System.out.println("服务器已经启动了");
		while(true){
			//让通道管理器至少选择一个通道
			keys=this.selector.select();
			Iterator<SelectionKey> it=this.selector.selectedKeys().iterator();
			if(keys>0){
				//进行轮询
				while(it.hasNext()){
					SelectionKey key=it.next();
					it.remove();
					//客户端连接事件
					if(key.isAcceptable()){
						serverChannel=(ServerSocketChannel) key.channel();
						//获得和客户端连接的通道
						SocketChannel channel=serverChannel.accept();
						channel.configureBlocking(false);//设置非阻塞方式
						
						//给客户端发消息
						channel.write(ByteBuffer.wrap(new String("hello").getBytes()));
						//还需要读取客户端过来的数据，所以注册一个去读数据的事件
						channel.register(this.selector, SelectionKey.OP_READ);
					}else if(key.isReadable()){
						read(key);
					}
				}
			}
			else{
				System.out.println("Select finished without any keys");
			}
		}
		
	}
	
	//根据SelectionKey对象来读取客户端发送到通道里的数据
	public void read(SelectionKey key) throws IOException {
		SocketChannel channel=(SocketChannel) key.channel();
		//缓冲区 
		ByteBuffer buff=ByteBuffer.allocate(1024);
		int len=channel.read(buff);
		String msg="服务端收到的消息是："+new String(buff.array(),0,len);
		System.out.println(msg);
	}
	
	public void start(){
		try{
			NIOSelectorServer ns=new NIOSelectorServer();
			ns.initServer();
			ns.listen();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new NIOSelectorServer().start();
	}

}