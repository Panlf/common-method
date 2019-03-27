package com.plf.common.socket.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		Socket socket = new Socket("127.0.0.1",8080);
		Scanner scanner=null;
		try {
			while(true){
				//获取Socket流中输出流，将数据发送给服务端
				OutputStream os = socket.getOutputStream();
				scanner = new Scanner(System.in);
				System.out.println("给服务器端发送内容:");
				String strNext = scanner.nextLine();
				os.write(strNext.getBytes());
				
				//拿到服务器的内容
				InputStream is = socket.getInputStream();
				byte[] buf= new byte[1024];
				int len = is.read(buf);
				String result = new String(buf,0,len);
				System.out.println("服务器端回复内容:"+result);
				
				
			}
		} catch (Exception e) {
			
		}finally {
			scanner.close();
			socket.close();
		}
		
	}

}
