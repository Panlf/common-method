package com.plf.common.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class AddressByIP {

	public static void main(String[] args) {
		System.out.println(new AddressByIP().getAddressByIP("116.192.175.93"));
	}

	private JSONObject getAddressByIP(String ip){
		//使用淘宝IP库
		String path="http://ip.taobao.com/service/getIpInfo.php?ip="+ip;
		JSONObject data=null;
		try {
			URL url=new URL(path);
			URLConnection conn=url.openConnection();
			conn.connect();
			InputStream ins=conn.getInputStream();
			InputStreamReader insread=new InputStreamReader(ins,"utf-8");
			BufferedReader buff=new BufferedReader(insread);
			String nextline=buff.readLine();
			if(nextline != null){
				//System.out.println(nextline);
				data=JSON.parseObject(nextline);
				//System.out.println("JSON DATA===>"+data);
			}
			return data;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
