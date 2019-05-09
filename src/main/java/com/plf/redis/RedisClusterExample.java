package com.plf.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class RedisClusterExample {

	@Test
	public void test01(){
		Set<HostAndPort> set = new HashSet<>();
		set.add(new HostAndPort("127.0.0.1",7001));
		set.add(new HostAndPort("127.0.0.1",7002));
		set.add(new HostAndPort("127.0.0.1",7003));
		set.add(new HostAndPort("127.0.0.1",7004));
		set.add(new HostAndPort("127.0.0.1",7005));
		set.add(new HostAndPort("127.0.0.1",7006));
		JedisCluster cluster = new JedisCluster(set);
		
		String result = cluster.get("a");
		System.out.println(result);
		
		try {
			cluster.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
