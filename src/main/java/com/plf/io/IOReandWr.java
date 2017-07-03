package com.plf.io;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

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
}
