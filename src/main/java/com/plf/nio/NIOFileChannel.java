package com.plf.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NIOFileChannel {
	public static void main(String[] args){
		fileChanneldemo();
	}
	
	@SuppressWarnings("resource")
	public static void fileChanneldemo(){
		try{
			//定义缓冲区对象
			ByteBuffer buff=ByteBuffer.allocate(1024);
			//通过文件输入流获得文件通道对象（读取操作）
			FileChannel infc=new FileInputStream("E://a.txt").getChannel();
			//追加写入文件
			FileChannel outfc=new FileOutputStream("E://a.txt",true).getChannel();
			//读取数据
			buff.clear();
			int len=infc.read(buff);
			System.out.println(new String(buff.array(),0,len));
			
			
			//写数据
			ByteBuffer buff2=ByteBuffer.wrap("jack".getBytes());
			outfc.write(buff2);
			
			//关闭资源
			outfc.close();
			infc.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}