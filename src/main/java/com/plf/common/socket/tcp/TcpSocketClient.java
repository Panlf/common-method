package com.plf.common.socket.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TcpSocketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("127.0.0.1",8080);
		//获取Socket流中输出流，将数据发送给服务端
		OutputStream os = socket.getOutputStream();
		os.write("TCP发送数据".getBytes());
		
		//关闭连接
		socket.close();
		
	}

}
