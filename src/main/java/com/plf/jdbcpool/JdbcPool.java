package com.plf.jdbcpool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import com.mysql.jdbc.Driver;

public class JdbcPool implements IPool {

	private static String jdbcDriver="";
	
	private static String jdbcUrl="";
	
	private static String jdbcUsername="";
	
	private static String jdbcPassword="";
	
	private static int  initConnCount;
	
	private static int maxConnects;
	
	private static int incrementalcount;
	
	private static Vector<PooledConnection> vector = new Vector<PooledConnection>();
	
	public void init(){
		InputStream in=JdbcPool.class.getClassLoader().getResourceAsStream("jdbc.properties");
	
		Properties pt = new Properties();
		
		try {
			pt.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jdbcDriver = pt.getProperty("jdbcDriver");
      
		jdbcUrl = pt.getProperty("jdbcUrl");
		
		jdbcUsername = pt.getProperty("jdbcUsername");

		jdbcPassword = pt.getProperty("jdbcPassword");

		initConnCount =Integer.valueOf(pt.getProperty("initConnCount"));

		maxConnects = Integer.valueOf(pt.getProperty("maxConnects"));

		incrementalcount =  Integer.valueOf(pt.getProperty("incrementalcount"));
		
		try {
			Driver driver=(Driver) Class.forName(jdbcDriver).newInstance();
			DriverManager.registerDriver(driver);
			
			//创建数据的连接，然后把链接放到vector
			createConnections(initConnCount);
			
			
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized PooledConnection getConnection() {
		if(vector.size()<=0){
			System.out.println("连接池中还没有连接！");
			throw new RuntimeException("连接池中还没有连接！");
		}
		
		PooledConnection conn = getActionConnection();
		
		//连建池全为忙碌
		if(conn == null){
			//需要增长连接池大小
			createConnections(incrementalcount);
			
			conn = getActionConnection();
			
			while(conn == null){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				conn = getActionConnection();
			}
		}
		return conn;
	}
	
	
	//池中拿连接
	public PooledConnection getActionConnection(){
		 System.out.println("数据库池中连接数为 " + vector.size());
		for(PooledConnection conn:vector){
			if(!conn.isBusy()){
				Connection trueconn = conn.getConn();
				try {
					//连接失效，重新创建
					if(!trueconn.isValid(3000)){
						Connection newconn = DriverManager.getConnection(jdbcUrl,jdbcUsername,jdbcPassword);
						
						conn.setConn(newconn);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				conn.setBusy(true);
				
				return conn;
			}
		}
		
		
		return null;
	}

	@Override
	public void createConnections(int count) {
		System.out.println("增加连接数，count="+count);
		for(int i=0;i<count;i++){
			//创建连接的时候，必须判断这个池中的连接要小于最大连接数
			if(maxConnects > 0 && vector.size() >= maxConnects){
				System.out.println("连接池中的连接数量已经达到了最大值！");
			
				throw new RuntimeException("连接池中的连接数量已经达到了最大值！");
			}else{
			
				try {
					Connection conn = DriverManager.getConnection(jdbcUrl,jdbcUsername,jdbcPassword);
					
					vector.add(new PooledConnection(conn, false));
					
	
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			
			}
		}
	}

}
