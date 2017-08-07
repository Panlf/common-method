package com.plf.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

public class NIOChannelClient {
	private SocketChannel channel=null;
	private ByteBuffer  buff=ByteBuffer.allocate(1024);
	private IntBuffer intBuff=buff.asIntBuffer();
	
	//与服务商指定的地址和端口建立连接通道
	public SocketChannel connect() throws IOException{
		return SocketChannel.open(new InetSocketAddress("127.0.0.1",8888));
	}
	
	public void sendRequest(int a,int b) throws IOException{
		intBuff.put(0,a);
		intBuff.put(1,b);
		channel.write(buff);
		System.out.println("发送加法请求("+a+"+"+b+")");
	}
	
	//接受服务商的运算结果
	public int receiveResult() throws IOException{
		buff.clear();
		channel.read(buff);
		return intBuff.get(0);
	}
	
	public int getSum(int a,int b){
		int result=0;
		try{
			channel=connect();
			sendRequest(a, b);
			result=receiveResult();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void main(String[] args){
		int result=new NIOChannelClient().getSum(35, 34);
		System.out.println("加法的结果是："+result);
	}
}
