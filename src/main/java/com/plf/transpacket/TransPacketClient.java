package com.plf.transpacket;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TransPacketClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		FileInputStream fis = null;

		DataOutputStream dos = null;

		Socket client = new Socket("127.0.0.1", 8080);
		try {
			File file = new File("E:\\test\\test.mp4");

			if (file.exists()) {
				fis = new FileInputStream(file);
				dos = new DataOutputStream(client.getOutputStream());

				dos.writeUTF("开始传输文件,文件名:" + file.getName() + ",文件长度:" + file.length());
				dos.flush();

				byte[] bytes = new byte[1024];
				int length = 0;
				while ((length = fis.read(bytes, 0, bytes.length)) > 0) {
					dos.write(bytes, 0, length);
					dos.flush();
				}

				dos.writeUTF("传输完成!!!");
				dos.flush();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
			if (fis != null) {
				fis.close();
			}
			if (dos != null) {
				dos.close();
			}
		}
	}
}
