package com.plf.common.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

public class URLHttpsUtils {

	public static String sendGet(String path) throws NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException{
		SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
		sslcontext.init(null, new TrustManager[] { new MyX509TrustManager() }, new java.security.SecureRandom());

		HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
			public boolean verify(String s, SSLSession sslsession) {
				System.out.println("WARNING: Hostname is not matched for cert.");
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
		HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
	
		String result="";
		BufferedReader buff=null;
		try {
			URL url=new URL(path);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			//设置通用属性
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");
			
			conn.connect();
			
			InputStream ins=conn.getInputStream();
			InputStreamReader insread=new InputStreamReader(ins,"utf-8");
			buff=new BufferedReader(insread);
			String line;
			while((line=buff.readLine())!=null){
				if(result!=null){
					result += "\n";
				}
				result += line;
			}
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(buff!=null){
				try {
					buff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
