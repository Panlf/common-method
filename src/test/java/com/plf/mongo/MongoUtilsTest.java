package com.plf.mongo;

import org.junit.Test;

import com.mongodb.client.MongoIterable;


public class MongoUtilsTest {

	private static MongoUtils mongoUtils=MongoUtils.getInstance("127.0.0.1:27017");
	@Test
	public void Test(){
		mongoUtils.createCollection("aa", "bb");
		MongoIterable<String> list= mongoUtils.listDataBase();
		for (String string : list) {
			System.out.println(string);
		}
	}
}
