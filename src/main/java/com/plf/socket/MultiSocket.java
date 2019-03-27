package com.plf.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import org.junit.Test;

public class MultiSocket {
	
	@Test
	public void Server() throws Exception{
		int port =8000;
		@SuppressWarnings("resource")
		ServerSocket server = new ServerSocket(port);
		while(true){
			Socket socket = server.accept();
			new Thread(new Runnable() {
				public void run() {
					try {
						handleSocket(socket);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	
	private void handleSocket(Socket socket) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"GBK"));
		StringBuilder sb = new StringBuilder();
		String temp;
		int index;
		
		while((temp=br.readLine())!= null){
			System.out.println("temp:"+temp);
			if((index=temp.indexOf("eof"))!=-1){
				sb.append(temp.substring(0,index));
				break;
			}
			sb.append(temp);
		}
		
		System.out.println("From client:"+sb.toString());
		
		
		Writer writer = new OutputStreamWriter(socket.getOutputStream(),"UTF-8");
		writer.write("你好，客户端");
		writer.write("eof\n");
		writer.flush();
		
		writer.close();
		br.close();
		socket.close();
	}
	
	
	@Test
	public void Client(){
		String host ="127.0.0.1";
		int port =8000;
		try {
			Socket client = new Socket(host, port);
			Writer writer = new OutputStreamWriter(client.getOutputStream(),"GBK");
			writer.write("你好，服务端");
			writer.write("eof\n");
			writer.flush();
			
			BufferedReader br = new BufferedReader(
						new InputStreamReader(client.getInputStream(),"UTF-8"));
			
			StringBuilder sb = new StringBuilder();
			String temp;
			int index;
			//设置10s超时时间
			client.setSoTimeout(10*1000);
			try {
				while((temp = br.readLine())!= null){
					if((index=temp.indexOf("eof"))!=-1){
						sb.append(temp.substring(0,index));
						break;
					}
					sb.append(temp);
				}
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("From server:"+sb.toString());
			br.close();
			writer.close();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
