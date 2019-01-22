package com.plf.url;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import org.junit.Test;

/**
 * 测试不同的网页，有些https可访问，有些不能访问
 * @author plf 2018年7月6日上午8:54:03
 *
 */
public class URLUtilsTest {

	/*
	 *  测试HTTPS不能使用
	 *  
	 * */
	@Test
	public void test(){
		String result=URLUtils.sendGet("https://www.biqudu.com/0_409/1448364.html");
		System.out.println(result==null?"":result.length());
		
		System.out.println("=========");
		
		//使用修改过的Https的通用方法
		try {
			String results=URLHttpsUtils.sendGet("https://www.biqudu.com/0_409/1448364.html");
			System.out.println(results==null?"":results.length());
		} catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException e) {
			
		}
		
	}
	
	/**
	 * 可访问
	 */
	@Test
	public void testBaidu(){
		String result=URLUtils.sendGet("https://www.baidu.com/");
		System.out.println(result);
	}
}
