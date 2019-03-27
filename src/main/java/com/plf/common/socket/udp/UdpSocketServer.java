package com.plf.common.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpSocketServer {

	public static void main(String[] args) throws IOException {
		//1、创建Socket服务
		DatagramSocket datagramSocket = new DatagramSocket(8080);
		
		//2、定义接受数据格式
		byte[] buf = new byte[1024];
		
		DatagramPacket datagramPacket = new DatagramPacket(buf,buf.length);

		//3、接受数据
		datagramSocket.receive(datagramPacket);
		
		String ip = datagramPacket.getAddress().getHostAddress();
		
		int port = datagramPacket.getPort();
		
		String result = new String(datagramPacket.getData(),0,datagramPacket.getLength());
		
		System.out.println(ip+":"+port+"--"+result);
		
		datagramSocket.close();
	}

}
