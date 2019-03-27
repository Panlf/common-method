package com.plf.common.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpSocketClient {
	public static void main(String[] args) throws SocketException {
		//1、建立可以实现UDP传输的Socket服务
		DatagramSocket datagramSocket = new DatagramSocket();
		//2、明确发送的数据
		String data = "UDP数据";
		byte[] bytes = data.getBytes();
		try {
			//3、通过Socket服务将具体d的数据发送出去
			InetAddress ip = InetAddress.getByName("127.0.0.1");
			DatagramPacket datagramPacket = new DatagramPacket(bytes,bytes.length,ip,8080);
			datagramSocket.send(datagramPacket);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//4、关闭服务
			datagramSocket.close();
		}
	}
}
