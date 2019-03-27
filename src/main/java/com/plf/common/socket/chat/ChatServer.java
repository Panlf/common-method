package com.plf.common.socket.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
	public static void main(String[] args) throws IOException {
		//1、创建Socket服务器端
		ServerSocket socket = new ServerSocket(8080);
		//2、获取与客户端进行连接
		Socket accept  = socket.accept();
		Scanner scanner = null;
		try {
			while(true){
				InputStream is = accept.getInputStream();
				byte[] buf= new byte[1024];
				int len = is.read(buf);
				
				String result = new String(buf,0,len);
				
				System.out.println("客户端发送的内容:"+result);
				
				OutputStream os = accept.getOutputStream();
				scanner = new Scanner(System.in);
				System.out.println("回复给客户端内容:");
				String strNext = scanner.nextLine();;
				os.write(strNext.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//3、关闭连接
			socket.close();
			accept.close();
			scanner.close();
		}
		
		
		
	}
}
