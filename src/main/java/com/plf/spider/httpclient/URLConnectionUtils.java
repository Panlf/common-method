package com.plf.spider.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLConnectionUtils {

	
	public static String get(String urlPath) {
		String result = "";
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(urlPath);
			
			HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();
			
			urlConnection.setRequestMethod("GET");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");
			urlConnection.setConnectTimeout(3000);
			
			inputStream = urlConnection.getInputStream();
			
			reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String line;
			
			while((line = reader.readLine())!=null) {
				result += line+"\n";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	
	public static String post(String urlPath,String params) {
		String result = "";
		InputStream inputStream = null;
		BufferedReader reader = null;
		OutputStream outStream = null;
		try {
			URL url = new URL(urlPath);
			
			HttpURLConnection urlConnection =  (HttpURLConnection) url.openConnection();
			
			// 允许向 URL 输出内容
			urlConnection.setDoOutput(true);
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");
			urlConnection.setConnectTimeout(3000);
			
			outStream = urlConnection.getOutputStream();
			outStream.write(params.getBytes());
			
			inputStream = urlConnection.getInputStream();
			
			reader = new BufferedReader(new InputStreamReader(inputStream));
			
			String line;
			
			while((line = reader.readLine())!=null) {
				result += line+"\n";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		String urlPath = "";
		String result  = get(urlPath);
		System.out.println(result);
	}
}
