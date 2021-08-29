package com.plf.common.socket;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 客户端写服务端读
 * @author plf 2019年3月27日上午9:35:33
 */
public class SocketStart {

	@Test
	public void Server(){
		int port = 8000;
		try {
			ServerSocket server = new ServerSocket(port);
			//阻塞式，接受请求
			Socket socket = server.accept();
			Reader reader = new InputStreamReader(socket.getInputStream());
			char[] chars = new char[64];
			int len;
			StringBuilder sb = new StringBuilder();
			while((len=reader.read(chars))!= -1){
				sb.append(new String(chars,0,len));
			}
			
			System.out.println("From client:"+sb.toString());
			reader.close();
			socket.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void Client(){
		String host ="127.0.0.1";
		int port =8000;
		try {
			Socket client = new Socket(host, port);
			Writer writer = new OutputStreamWriter(client.getOutputStream());
			writer.write("Hello Socket");
			writer.flush();
			writer.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
