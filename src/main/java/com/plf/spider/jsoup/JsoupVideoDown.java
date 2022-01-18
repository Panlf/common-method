package com.plf.spider.jsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.select.Elements;

public class JsoupVideoDown {

	public static void main(String[] args) {
		try {
			String path="";
			downVideo(path,"fourBeauty");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//下载大文件速度很慢需要改进
	public static void downVideo(String path,String fileName) throws IOException{
		URL url = new URL(path);
		URLConnection uc = url.openConnection();
		InputStream is = uc.getInputStream();
		byte[] bs = new byte[1024];
		FileOutputStream out = new FileOutputStream("E:\\temp\\"+fileName+".mp4");
		int i = 0;
		while ((i = is.read(bs)) != -1) {
			out.write(bs,0,i);
		} 
		is.close();
		out.close();
	}

}
