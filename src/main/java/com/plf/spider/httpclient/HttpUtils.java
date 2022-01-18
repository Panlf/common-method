package com.plf.spider.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpClient的访问Http工具类
 * @author plf 2018年7月20日下午9:25:41
 *
 */
public class HttpUtils {
	
	private static Logger logger=LoggerFactory.getLogger(HttpUtils.class); 
	//静态内部类的单例模式
	private HttpUtils(){}
	
	private static class HttpUtilsInstance {
        private static final HttpUtils INSTANCE = new HttpUtils();
    }
	
	public static HttpUtils getInstance() {
        return HttpUtilsInstance.INSTANCE;
    }
	
	private static BasicCookieStore cookieStore = new BasicCookieStore();
	
	 
	//携带的信息
    private static HttpClientContext context = HttpClientContext.create();
    
    private static RequestConfig globalConfig  = RequestConfig.custom()
            .setConnectTimeout(5000)   //设置连接超时时间
            .setConnectionRequestTimeout(5000) // 设置请求超时时间
            .setSocketTimeout(5000)
            .setRedirectsEnabled(true)//默认允许自动重定向
            .setCookieSpec(CookieSpecs.STANDARD_STRICT)
            .build();
    
	// 获取当前客户端对象
  	private static CloseableHttpClient httpClient = HttpClients
  			.custom().setDefaultRequestConfig(globalConfig)
  			.setDefaultCookieStore(cookieStore)
  			.build(); 
    
	public String sendGet(String url){
		logger.info("进入Get请求，当前的URL=====>{}",url);
		CloseableHttpResponse response=null;
		String result = null; 
		HttpEntity entity=null;
		try{
			HttpGet get = new HttpGet(url); 
			get.setConfig(globalConfig);
			get.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"));
			//通过请求对象获取响应对象
            response = httpClient.execute(get, context);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            	entity = response.getEntity();
            	result= EntityUtils.toString(entity,"utf-8");
             } 
            EntityUtils.consume(entity); //关闭
            return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String sendPost(String url,Map<String,String> map){
		logger.info("进入Post请求，当前的URL=====>{}",url);
		CloseableHttpResponse response = null;    
        String result = null;
        HttpEntity entity=null;
        try{
            HttpPost post = new HttpPost(url); 
            post.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"));
            post.setConfig(globalConfig);
            if(!map.isEmpty()){
            	List <NameValuePair> nvps = new ArrayList <NameValuePair>();
    			for (Map.Entry<String, String> entry : map.entrySet()) {
    				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
    			post.setEntity(new UrlEncodedFormEntity(nvps,"utf-8"));
            }
          //执行请求用execute方法    
          response = httpClient.execute(post, context);  
          if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
        	  logger.info("请求成功...");
        	  entity = response.getEntity();
          	  result= EntityUtils.toString(entity,"utf-8");
          	    //获取cookie
          		List<Cookie> cookies = cookieStore.getCookies();
          	    if (cookies.isEmpty()) {
          	    	logger.info("当前没有获取到cookie或者没有cookie");
          	    }
           } 
          EntityUtils.consume(entity); //关闭
          return result;
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
	}
}
