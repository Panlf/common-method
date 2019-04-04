package com.plf.common.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

/**
 * IO的读写文本
 * @author plf 2019年4月4日下午4:06:26
 *
 */
public class IOReandWr {

	@Test
	public void writeTxt() {
		FileWriter fw = null;
		try {
			fw = new FileWriter("E:\\demo.txt", true);
			fw.write("plf");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null)
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
			}
		}
	}
	
	@Test
	public void readTxt(){
		try {
			FileReader fr=new FileReader("E:\\demo.txt");
			char[] buf=new char[1024];
			int len=0;
			while((len=fr.read(buf))!=-1){
				System.out.println(new String(buf,0,len));
			}
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void writeByBuffer(){
		try {
			FileWriter fw=new FileWriter(new File("E:\\demo.txt"),true);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write("plf");
			bw.newLine();
			bw.write("pcq");
			//\r\n换行
			bw.write("two people\r\nThey are lover");
			bw.flush();
			fw.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readByBuffer(){
		try {
			FileReader fr=new FileReader(new File("E:\\demo.txt"));
			BufferedReader br=new BufferedReader(fr);
			String line=null;
			while((line=br.readLine())!=null){
				System.out.println(line);
			}
			fr.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
