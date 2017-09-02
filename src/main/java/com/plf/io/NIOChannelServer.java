package com.plf.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NIOChannelServer {
	
	private ByteBuffer  buff=ByteBuffer.allocate(1024);
	//创建一个int缓冲区的视图，此缓冲区内容的更改在新缓冲区中是可见的，反之亦然
	private IntBuffer intBuff=buff.asIntBuffer();
	private SocketChannel clientChannel=null;
	private ServerSocketChannel serverChannel=null;
	
	public void openChannel() throws IOException{
		//建立一个新的连接通道
		serverChannel=ServerSocketChannel.open();
		//为新的通道设置访问的接口
		serverChannel.socket().bind(new InetSocketAddress(8888));
		System.out.println("服务通道已经打开...");
	}
	
	//等待客户端的连接
	public void waitReqConn() throws IOException {
		while(true){
			clientChannel=serverChannel.accept();
			if(null != clientChannel){
				System.out.println("新的连接加入！");
			}
			
			processReq();//处理请求
			clientChannel.close();
		}
		
	}
	
	//处理请求过来的数据
	public void processReq() throws IOException {
		System.out.println("开始读取和处理客户端的数据");
		buff.clear();//把当前位置设置为0，上限值修改为容量的值
		clientChannel.read(buff);
		int result=intBuff.get(0)+intBuff.get(1);
		buff.flip();
		buff.clear();
		//修改视图，原来的缓冲区也会变化
		intBuff.put(0,result);
		clientChannel.write(buff);
		System.out.println("读取和处理客户端数据完成");
		
	}

	public void start(){
		try{
			//打开服务通道
			openChannel();
			
			//监听等待客户端请求
			waitReqConn();
			
			clientChannel.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		new NIOChannelServer().start();
	}
}