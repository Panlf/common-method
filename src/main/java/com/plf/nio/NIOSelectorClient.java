package com.plf.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOSelectorClient {

	private Selector selector;
	private ByteBuffer outBuff=ByteBuffer.allocate(1024);
	private ByteBuffer inBuff=ByteBuffer.allocate(1024);
	private int keys=0;
	private SocketChannel channel=null;
	
	public void initClient() throws IOException{
		//获得一个socket通道，并没有进行连接
		channel = SocketChannel.open();
		//获得一个通道管理器
		selector = Selector.open();
		//设置为非阻塞
		channel.configureBlocking(false);
		
		//连接服务器
		channel.connect(new InetSocketAddress("127.0.0.1",8888));
		//注册客户端连接服务器事件
		channel.register(this.selector, SelectionKey.OP_CONNECT);
	}
	//监听在通道上面进行注册得事件
	@SuppressWarnings("static-access")
	public void listen() throws IOException{
		//进行轮询
		while(true){
			keys=this.selector.select();
			if(keys>0){
				Iterator<SelectionKey> it=this.selector.selectedKeys().iterator(); 
				while(it.hasNext()){
					SelectionKey key=it.next();
					//测试此通道是否完成套接字的连接
					if(key.isConnectable()){
						//获得与服务器相连的通道
						SocketChannel channel=(SocketChannel) key.channel();
						if(channel.isConnectionPending()){
							channel.finishConnect();
							System.out.println("完成连接");
						}
						channel.register(this.selector, SelectionKey.OP_WRITE);
					}
					//在通道上面进行写操作
					else if(key.isWritable()){
						SocketChannel channel=(SocketChannel) key.channel();
						outBuff.clear();
						System.out.println("客户端正在写数据...");
						channel.write(outBuff.wrap("我是ClientA".getBytes()));
						channel.register(this.selector, SelectionKey.OP_READ);
						System.out.println("客户端写数据完成");
					}
				//在通道上进行读取
				else if(key.isReadable()){
					SocketChannel channel=(SocketChannel) key.channel();
					inBuff.clear();
					System.out.println("client start read data");
					channel.read(inBuff);
					System.out.println("==>"+new String(inBuff.array()));
					System.out.println("client finish read data");
					}
				}
			}
			else{
				System.out.println("没有找到感兴趣的事件");
			}
		}
	}	
	public void start(){
		try{
			initClient();
			listen();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new NIOSelectorClient().start();
	}

}