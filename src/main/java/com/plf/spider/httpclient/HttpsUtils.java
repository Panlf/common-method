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
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

/**
 * HttpClient的访问Https工具类
 * @author plf 2018年7月20日下午9:25:29
 *
 */
public class HttpsUtils {

	private static Logger log = LoggerFactory.getLogger(HttpsUtils.class);
    private HttpsUtils(){}

    private static class HttpUtilsInstance {
        private static final HttpsUtils INSTANCE = new HttpsUtils();
    }

    public static HttpsUtils getInstance() {
        return HttpUtilsInstance.INSTANCE;
    }

    private static final String HTTP = "http";
    private static final String HTTPS = "https";
    private static SSLConnectionSocketFactory sslConnectionSocketFactory = null;
    private static PoolingHttpClientConnectionManager poolConnManager = null;

    static{
        try{
            SSLContext sslcontext = SSLContexts.custom()
                    .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                    .build();
            HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
            sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslcontext, hostnameVerifier);

            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create()
                    .register(HTTP,
                            PlainConnectionSocketFactory.getSocketFactory())
                    .register(HTTPS, sslConnectionSocketFactory).build();

            poolConnManager = new PoolingHttpClientConnectionManager(
                    socketFactoryRegistry);
            poolConnManager.setMaxTotal(200);
            poolConnManager.setDefaultMaxPerRoute(20);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static BasicCookieStore cookieStore = new BasicCookieStore();

    /**
     * 携带的信息
     */
    private static HttpClientContext context = HttpClientContext.create();

    private static RequestConfig globalConfig  = RequestConfig.custom()
            //设置连接超时时间
            .setConnectTimeout(5000)
            // 设置请求超时时间
            .setConnectionRequestTimeout(5000)
            .setSocketTimeout(5000)
            //默认允许自动重定向
            .setRedirectsEnabled(true)
            .setCookieSpec(CookieSpecs.STANDARD_STRICT)
            .build();

    /**
     * 获取当前客户端对象
     */
    private static CloseableHttpClient httpClient = HttpClients.custom().disableCookieManagement()
                .setSSLSocketFactory(sslConnectionSocketFactory)
                .setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore)
                .setConnectionManager(poolConnManager)
                .setConnectionManagerShared(true)
                .build();

    public String sendGet(String url){
        log.info("进入Get请求，当前的URL=====>{}",url);
        CloseableHttpResponse response;
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
            EntityUtils.consume(entity);
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String sendPost(String url,Map<String,String> map){
        log.info("进入Post请求，当前的URL=====>{}",url);
        CloseableHttpResponse response;
        String result = null;
        HttpEntity entity=null;
        try{
            HttpPost post = new HttpPost(url);
            post.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36"));
            post.addHeader(new BasicHeader("Accept", "*/*"));
            post.addHeader(new BasicHeader("Connection", "Keep-Alive"));
            post.addHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
            post.setConfig(globalConfig);
            if(!map.isEmpty()){
                List <NameValuePair> nameValuePair = new ArrayList <NameValuePair>();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    nameValuePair.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                post.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
            }
            //执行请求用execute方法
            response = httpClient.execute(post, context);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                log.info("请求成功...");
                entity = response.getEntity();
                result= EntityUtils.toString(entity,"utf-8");
                //获取cookie
                List<Cookie> cookies = cookieStore.getCookies();
                if (cookies.isEmpty()) {
                    log.info("当前没有获取到cookie或者没有cookie");
                }
            }
            EntityUtils.consume(entity);
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
