package com.plf.socket;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

public class SocketWriterRead {

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
			
			String temp;
			int index;
			
			while((len=reader.read(chars))!= -1){
				temp = new String(chars,0,len);
				if((index=temp.indexOf("eof"))!=-1){
					sb.append(temp.substring(0,index));
					break;
				}
				sb.append(temp);
			}
			
			System.out.println("From client:"+sb.toString());
			
			Writer writer = new OutputStreamWriter(socket.getOutputStream());
			writer.write("Hello Client");
			writer.write("eof");
			writer.flush();
			writer.close();
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
			writer.write("eof");
			writer.flush();
			
			Reader reader = new InputStreamReader(client.getInputStream());
			char[] chars = new char[64];
			int len;
			StringBuilder sb = new StringBuilder();
			String temp;
			int index;
			
			while((len=reader.read(chars))!= -1){
				temp = new String(chars,0,len);
				if((index=temp.indexOf("eof"))!=-1){
					sb.append(temp.substring(0,index));
					break;
				}
				sb.append(temp);
			}
			
			System.out.println("From server:"+sb.toString());
			reader.close();
			
			writer.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
