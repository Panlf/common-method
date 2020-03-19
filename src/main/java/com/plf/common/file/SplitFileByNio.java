package com.plf.common.file;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class SplitFileByNio {
	// 2MB
	private static int SIZE = 1024 * 1024 * 2;
	
	public static void main(String[] args) throws IOException  {
		spilt("E:\\partfiles\\a.pdf");
	}

	public static void  spilt(String path) throws IOException {
		RandomAccessFile fin = new RandomAccessFile(path, "r");
		FileChannel fcin = fin.getChannel();
		FileOutputStream fout =null;
		ByteBuffer buffer = ByteBuffer.allocate(SIZE);

		int n = 0;
		int flag = 0;
		while (true) {
			buffer.clear();
			flag = fcin.read(buffer);
			if(flag==-1){
				break;
			}
			buffer.flip();
			fout = new FileOutputStream("E:\\partfiles\\"+(n++)+".part");
			FileChannel fcout = fout.getChannel();
			fcout.write(buffer);
		}
		fin.close();
		fout.close();
	}
}
