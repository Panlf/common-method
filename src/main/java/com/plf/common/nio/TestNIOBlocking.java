package com.plf.common.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/*
 * 一、使用 NIO 完成网络通信的三个核心：
 * 
 * 1. 通道（Channel）：负责连接
 * 		
 * 	   java.nio.channels.Channel 接口：
 * 			|--SelectableChannel
 * 				|--SocketChannel
 * 				|--ServerSocketChannel
 * 				|--DatagramChannel
 * 
 * 				|--Pipe.SinkChannel
 * 				|--Pipe.SourceChannel
 * 
 * 2. 缓冲区（Buffer）：负责数据的存取
 * 
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 * 
 */
public class TestNIOBlocking {
	@Test
	public void client() throws IOException{
		//1、获取通道
		SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
		
		FileChannel inChannel = FileChannel.open(Paths.get("E://temp//37.jpg"), StandardOpenOption.READ);
		
		
		//2、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//3、读取本地文件，并发送到服务器
		while(inChannel.read(buf) != -1){
			buf.flip();
			sChannel.write(buf);
			buf.clear();
		}
		
		//4、关闭通道
		inChannel.close();
		sChannel.close();
	}
	
	
	@Test
	public void server() throws IOException{
		//1、获取通道
		ServerSocketChannel ssChannel = ServerSocketChannel.open();
		
		FileChannel outChannel = FileChannel.open(Paths.get("E://temp//copy——37.jpg"), StandardOpenOption.WRITE,StandardOpenOption.CREATE);
		
		//2、绑定连接
		ssChannel.bind(new InetSocketAddress("127.0.0.1", 8080));
		
		//3、获取客户端连接的通道
		SocketChannel sChannel = ssChannel.accept();
		
		//4、分配指定大小的缓冲区
		ByteBuffer buf = ByteBuffer.allocate(1024);
		
		//5、获取客户端的数据，并保存到本地
		while(sChannel.read(buf)!=-1){
			buf.flip();
			outChannel.write(buf);
			buf.clear();
		}
		
		//6、关闭通道
		ssChannel.close();
		outChannel.close();
		sChannel.close();
	}
}
