package com.plf.spider.jsoup;

//import java.io.ByteArrayOutputStream;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.OutputStream;
//import java.net.MalformedURLException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupImgDown {

	public static void main(String[] args) {
		// String html = "<html><head><title>First parse</title></head>"
		// + "<body><p id='kky'>Parsed HTML into a doc.</p></body></html>";
		// Document doc = Jsoup.parse(html);
		// Element body = doc.body();
		// System.out.println(body);
		// System.out.println(doc.getElementById("kky").text());
		try {
			for(int i=0;i<9;i++){
				int k=80+i;
				//Thread.sleep(5000);
				//--Document doc = Jsoup.connect("http://www.ivsky.com/tupian/labuladuoquan_v41320/pic_6642"+k+".html").get();
				Document doc = Jsoup.connect("http://www.ivsky.com/tupian/nvsheng_v39311/pic_6340"+k+".html").get();
				
				// String title = doc.title();
				if(doc!=null){
					String path=doc.getElementById("imgis").attr("src");
					System.out.println(path);
					downPic(path,k+"b");
				}
				//downloadPicture(path,k+"");
			}
			//downPic(path);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void downPic(String path,String filename) throws IOException {
			URL url = new URL(path);
			
			URLConnection uc = url.openConnection();
			//uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			InputStream is = uc.getInputStream();
			byte[] bs = new byte[1024];
			FileOutputStream out = new FileOutputStream("E:\\temp\\"+filename+".jpg");
			int i = 0;
			while ((i = is.read(bs)) != -1) {
				out.write(bs,0,i);
			} 
			is.close();
			out.close();
		}
	
	
//	private static void downloadPicture(String urlList,String filename) {
//        URL url = null;
//        try {
//            url = new URL(urlList);
//            DataInputStream dataInputStream = new DataInputStream(url.openStream());
//
//            String imageName =  "E:/temp/"+filename+".jpg";
//
//            FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
//            ByteArrayOutputStream output = new ByteArrayOutputStream();
//
//            byte[] buffer = new byte[1024];
//            int length;
//
//            while ((length = dataInputStream.read(buffer)) > 0) {
//                output.write(buffer, 0, length);
//            }
//           // byte[] context=output.toByteArray();
//            fileOutputStream.write(output.toByteArray());
//            dataInputStream.close();
//            fileOutputStream.close();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

