package com.plf.mongo;

import com.mongodb.client.MongoIterable;
import org.junit.jupiter.api.Test;


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
