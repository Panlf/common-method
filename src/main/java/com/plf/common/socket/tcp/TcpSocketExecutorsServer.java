package com.plf.common.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TcpSocketExecutorsServer {
	public static void main(String[] args) throws IOException {

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		// 1、创建Socket服务器端
		ServerSocket socket = new ServerSocket(8080);
		try {
			while (true) {
				newCachedThreadPool.execute(new Runnable() {
				// 2、获取与客户端进行连接
				Socket accept = socket.accept();
				@Override
				public void run() {
					try {
						InputStream is = accept.getInputStream();
						byte[] buf = new byte[1024];
						int len = is.read(buf);
						String result = new String(buf, 0, len);
						System.out.println(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			}
		} finally {
			socket.close();
		}
	}
}
