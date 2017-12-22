package com.plf.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class InAndOutProperties {
	public static void main(String[] args) {
		try {
			writeProperties();
			readProperties();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//读取properties
	public static void readProperties() throws IOException{
		Properties prop = new Properties(); 
		InputStream in = new BufferedInputStream (new FileInputStream("src/main/resources/test.properties"));
		prop.load(in); 
		Iterator<String> it=prop.stringPropertyNames().iterator();
		while(it.hasNext()){
		    String key=it.next();
		    System.out.println(key+":"+prop.getProperty(key));
		}
		in.close();
	}
	
	public static void writeProperties() throws IOException{
		Properties prop = new Properties(); 
		FileOutputStream out = new FileOutputStream("src/main/resources/test.properties", true);//true表示追加打开
		prop.setProperty("key3", "value3");
		prop.store(out, "\n add properties");
		out.close();
	}
}

