package com.plf.spider.httpclient;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;*/


/**
 * httpClient发送请求
 * @author Panlf 2017年7月18日下午10:31:35
 *
 */
public class HttpClientExample {
	private static HttpUtils httpUtils=HttpUtils.getInstance();
	//GET请求

	@Test
	public void TestGet() {
		String url="http://zhannei.baidu.com/cse/search?q=斗破苍穹&click=1&s=13603361664978768713&nsid=";
		String result=httpUtils.sendGet(url);
		System.out.println(result);
	}
	
	//分析URL
	@Test
	public void analyzeURL(){
		String path="http://www.27270.com/ent/meinvtupian/2017/237717.html";
		String uuid="";
		try {
			URL url=new URL(path);
			//增加代理功能
			Proxy proxy =new Proxy(Proxy.Type.HTTP,new InetSocketAddress("180.101.205.253",8888));
			URLConnection conn=url.openConnection(proxy);
			conn.connect();
			InputStream ins=conn.getInputStream();
			InputStreamReader insread=new InputStreamReader(ins,"utf-8");
			BufferedReader buff=new BufferedReader(insread);
			String nextline=buff.readLine();
			while(nextline != null){
				if(!getPattern(nextline).isEmpty()){
					getPattern(nextline);
					uuid=UUID.randomUUID().toString();//随机获取一个UUID
					downloadPicture(getPattern(nextline),uuid);
				}
				nextline=buff.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	//测试正则表达式
	@Test
	public void TestUtil(){
		String str="<p><img src=\"http://img1.tuicool.com/RR3eyyE.jpg!web\" class=\"alignCenter\" /> </p>";
		System.out.println(getPattern(str));
	}
	
	//图片正则表达式
	public static String getPattern(String str){
		String pattern="src=\"http:.+?\\.(jpg|gif|png)";
		Pattern pa=Pattern.compile(pattern);
		Matcher ma=pa.matcher(str);
		if(ma.find()){
			//System.out.println(ma.group().replace("src=\"", ""));
			return ma.group().replace("src=\"", "");
		}
		return "";
	}
	
	//根据URL下载图片
	private static void downloadPicture(String urlList,String filename) {
		  URL url = null;
		  try {
		      url = new URL(urlList);
		      DataInputStream dataInputStream = new DataInputStream(url.openStream());
		
		      String imageName =  "E:/temp/"+filename+".jpg";
		
		      FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));
		      ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		      byte[] buffer = new byte[1024];
		      int length;
		
		      while ((length = dataInputStream.read(buffer)) > 0) {
		          output.write(buffer, 0, length);
		      }
		      //byte[] context=output.toByteArray();
		      fileOutputStream.write(output.toByteArray());
		      dataInputStream.close();
		      fileOutputStream.close();
			  } catch (MalformedURLException e) {
			      e.printStackTrace();
			  } catch (IOException e) {
			      e.printStackTrace();
		  }
	}
	
}
