package com.plf.common.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpSocketServer {
	public static void main(String[] args) throws IOException {
		//1、创建Socket服务器端
		ServerSocket socket = new ServerSocket(8080);
		//2、获取与客户端进行连接
		Socket accept  = socket.accept();
		String ip = accept.getInetAddress().getHostAddress();
		InputStream is = accept.getInputStream();
		byte[] buf= new byte[1024];
		int len = is.read(buf);
		
		String result = new String(buf,0,len);
		
		System.out.println(ip+"---"+result);
		//3、关闭连接
		socket.close();
		accept.close();
	}
}
