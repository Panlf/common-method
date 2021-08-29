package com.plf.redis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

/**
 * 单机版本
 * @author plf 2019年4月21日下午4:56:57
 *
 */
public class RedisExample {

	static Jedis jedis = null;
	
	static{
		jedis = new Jedis("127.0.0.1",6378);
        jedis.auth("123@456");
	}
	
	
	@Test
	public void test01(){
		String result = jedis.set("address", "浙江");
		System.out.println(result);
		
		/*result = jedis.get("address");
		System.out.println(result);
		
		result = jedis.set("address","上海");
		System.out.println(result);
		
		Long index = jedis.del("address");
		System.out.println(index);*/
	}
	
	@AfterEach
	public void close(){
		if(null != jedis){
			jedis.close();	
		}
	}
}
