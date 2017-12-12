package com.plf.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;


/**
 * MongoDB的工具类
 * @author plf 2017年11月19日下午7:15:43
 *
 */
public class MongoUtils {

	private static MongoUtils mongoUtils=null;
	private MongoClient mongoClient = null;  
	 
	private MongoUtils(String clientPath){
		if(mongoClient==null){
			
			MongoClientOptions.Builder builder=new MongoClientOptions.Builder();
			
			builder.maxWaitTime(1000 * 60 * 2);
			builder.connectTimeout(1000 * 60 * 1);
			builder.socketTimeout(0);
			builder.connectionsPerHost(200);
			builder.threadsAllowedToBlockForConnectionMultiplier(5000);
			builder.writeConcern(WriteConcern.ACKNOWLEDGED);
			
			MongoClientOptions options=builder.build();
			
			mongoClient=new MongoClient(clientPath,options);
		}
	}
	
	public static MongoUtils getInstance(String clientPath) {
		if(mongoUtils == null){
			mongoUtils=new MongoUtils(clientPath);
		}
        return mongoUtils;
    }
	
	/**
	 * 新建数据库  但是其实这个方法没什么意思，即使这个方法执行了，也是看不到效果的，查不到是否新建了
	 * 当新建collection的时候，在选择database的时候，如果没有这个数据库就会自动创建
	 * @param databaseName 数据库名称
	 * @return
	 */
	public MongoDatabase createDatabase(String databaseName){
		return mongoClient.getDatabase(databaseName);
	}
	
	/**
	 * 删除数据库
	 * @param databaseName 数据库名称
	 */
	public void dropDatabase(String databaseName){
		mongoClient.dropDatabase(databaseName);
	}
	
	/**
	 * 查询所有的mongodb的数据库
	 * @return
	 */
	public MongoIterable<String> listDataBase(){
		MongoIterable<String> dataBaseName=mongoClient.listDatabaseNames();
		return dataBaseName;
	}
	
	/**
	 * 新建collection，如果没有这个数据库则会自动创建
	 * @param databaseName 数据库名称
	 * @param collectionName 集合名称
	 */
	public void createCollection(String databaseName,String collectionName){
		mongoClient.getDatabase(databaseName).createCollection(collectionName);
	}
	
	
	
}
