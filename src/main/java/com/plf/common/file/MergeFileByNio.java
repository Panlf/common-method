package com.plf.common.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class MergeFileByNio {

	public static void main(String[] args) throws IOException {
		mergeFile2(new File("E:\\partfiles"));
	}

	public static void mergeFile(File dir) throws IOException {

		// dir目录下所有的碎片文件
		File[] partFiles = dir.listFiles((d, n) -> n.endsWith(".part"));
		RandomAccessFile raf = null;
		ByteBuffer[] buffers = new ByteBuffer[partFiles.length];
		for (int i = 0; i < partFiles.length; i++) {
			raf = new RandomAccessFile(partFiles[i], "r");
			FileChannel channel = raf.getChannel();
			buffers[i] = channel.map(FileChannel.MapMode.READ_ONLY, 0, raf.length());
		}
		FileOutputStream outFile = new FileOutputStream("E:\\partfiles\\new_b.pdf");
		FileChannel out = outFile.getChannel();
		out.write(buffers);
		out.close();
		outFile.close();
	}

	public static void mergeFile2(File dir) throws IOException {
		// dir目录下所有的碎片文件
		File[] partFiles = dir.listFiles((d, n) -> n.endsWith(".part"));

		FileOutputStream outFile = new FileOutputStream("E:\\partfiles\\new_b.pdf");
		FileChannel outChannel = outFile.getChannel();
		FileInputStream fcis = null;
		FileChannel fc  = null;
		
		for (File f : partFiles) {
			fcis = new FileInputStream(f);
			fc = fcis.getChannel();
			ByteBuffer bb = ByteBuffer.allocate(1024 * 1024 * 2);
			while (fc.read(bb) != -1) {
				bb.flip();
				outChannel.write(bb);
				bb.clear();
			}
		}
		outFile.close();
		outChannel.close();
		fc.close();
		fcis.close();
	}
}
