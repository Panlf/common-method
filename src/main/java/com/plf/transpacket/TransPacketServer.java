package com.plf.transpacket;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TransPacketServer {
	public static void main(String[] args) throws IOException {

		DataInputStream dis = null;

		FileOutputStream fos = null;

		// 1、创建Socket服务器端
		ServerSocket socket = new ServerSocket(8080);
		// 2、获取与客户端进行连接
		Socket accept = socket.accept();
		try {
			dis = new DataInputStream(accept.getInputStream());
			// 报头信息
			String fileInfo = dis.readUTF();
			System.out.println(fileInfo);
			File directory = new File("E:\\test\\copy");
			if (!directory.exists()) {
				directory.mkdir();
			}
			File file = new File(directory.getAbsolutePath() + File.separatorChar + "test.mp4");
			fos = new FileOutputStream(file);

			// 开始接收文件
			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = dis.read(bytes, 0, bytes.length)) > 0) {
				fos.write(bytes, 0, length);
				fos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 3、关闭连接
			if (socket != null) {
				socket.close();
			}
			if (accept != null) {
				accept.close();
			}

			if (fos != null) {
				fos.close();
			}
			if (dis != null) {
				dis.close();
			}
		}
	}
}
