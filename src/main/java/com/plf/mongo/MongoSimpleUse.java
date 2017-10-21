package com.plf.mongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
/**
 * Java操作Mongo的简单操作
 * @author plf 2017年10月21日下午3:31:53
 *
 */
public class MongoSimpleUse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//建立Mongo的数据库对象
		MongoClient mongo=new MongoClient("127.0.0.1:27017");
		//获取所有数据库
		MongoIterable<String> dataBaseName=mongo.listDatabaseNames();
		for (String string : dataBaseName) {
			System.out.println(string);
		}
		
		//连接数据库,获取所有的集合名
		MongoDatabase db=mongo.getDatabase("test");
		for(String name:db.listCollectionNames()){
			System.out.println(name);
		}
		
		//获取集合中的所有数据
		MongoCollection<Document> coll = db.getCollection("user");
		FindIterable<Document> data = coll.find();
		for (Document document : data) {
			System.out.println(document);
		}
		mongo.close();
	}

}
