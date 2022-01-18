package com.plf.spider.httpclient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ProxyIpIsAlive {

	public static void main(String[] args) {
		boolean b =new ProxyIpIsAlive().isAlive("180.101.205.253",8888);
		System.out.println(b);
	}
	
	
	public boolean isAlive(String ip,int port){
		  Socket socket = new Socket();
		  try {
			socket.connect(new InetSocketAddress(ip, port),1000);
			return socket.isConnected();
		  } catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		  return false;
	}

}
