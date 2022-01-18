package com.plf.spider.httpclient;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class HttpClientLogin {
	
	public static void main(String[] args) throws ParseException, IOException {
		// 创建cookie store的本地实例
		CookieStore cookieStore = new BasicCookieStore();
		
		 // 全局请求设置
		RequestConfig globalConfig  = RequestConfig.custom()
		            .setConnectTimeout(5000)   //设置连接超时时间
		            .setConnectionRequestTimeout(5000) // 设置请求超时时间
		            .setSocketTimeout(5000)
		            .setRedirectsEnabled(true)//默认允许自动重定向
		            .setCookieSpec(CookieSpecs.STANDARD_STRICT)
		            .build();
		 
		HttpClientContext context = HttpClientContext.create();
		
		context.setCookieStore(cookieStore);
		
		Collection<BasicHeader> colletion=new HashSet<BasicHeader>();
		colletion.add(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"));
		
		CloseableHttpClient httpClient = HttpClients
	  			.custom()
	  			.setDefaultRequestConfig(globalConfig)
	  			.setDefaultCookieStore(cookieStore)
	  			.setDefaultHeaders(colletion)
	  			.build(); 
		
		CloseableHttpResponse response=null;
		
		
		HttpGet get = new HttpGet("https://toutiao.io/");
		 
		try {
			response = httpClient.execute(get, context);
			System.out.println("登录前的常规Cookie:===============");
	        for (Cookie c : cookieStore.getCookies()) {
	        	 System.out.println(c.getName() + ": " + c.getValue());
	        }
	        response.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		get = new HttpGet("https://toutiao.io/signin-email"); 
		response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
    	String html= EntityUtils.toString(entity,"utf-8"); 
    	
        Document doc = Jsoup.parse(html);    
        Element form = doc.select("#main form").get(0);    
        String utf8 = form.select("input[name=utf8]").get(0).val();    
        String authenticity_token = form.select("input[name=authenticity_token]").get(0).val();    
        String commit = form.select("input[name=commit]").get(0).val(); 
        
        // 构造post数据
        List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
        valuePairs.add(new BasicNameValuePair("auth_key", ""));
        valuePairs.add(new BasicNameValuePair("password", ""));
        valuePairs.add(new BasicNameValuePair("utf8", utf8));
        valuePairs.add(new BasicNameValuePair("authenticity_token", authenticity_token));
        valuePairs.add(new BasicNameValuePair("commit", commit));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        formEntity.setContentType("application/x-www-form-urlencoded");
        
        HttpPost post = new HttpPost("https://toutiao.io/auth/identity/callback");
        post.setEntity(formEntity);
        response = httpClient.execute(post, context);
        entity = response.getEntity();
    	html= EntityUtils.toString(entity,"utf-8");  
    	
    	//writeText(html);
    	
        System.out.println("登陆成功后,新的Cookie:===============");
        for (Cookie c : context.getCookieStore().getCookies()) {
          System.out.println(c.getName() + ": " + c.getValue());
        }
        
        
        get = new HttpGet("https://toutiao.io/favorites");
        response = httpClient.execute(get, context);
        entity = response.getEntity();
    	html= EntityUtils.toString(entity,"utf-8"); 
    	writeText(html);
	}
	
	private static void writeText(String text){
		try {
			FileWriter fw=new FileWriter(new File("E:\\html.txt"),false);
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(text);
			bw.flush();
			fw.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
