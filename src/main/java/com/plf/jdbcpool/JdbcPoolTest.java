package com.plf.jdbcpool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcPoolTest {
	JdbcPool pool = DBManage.getInstance();
	
	@BeforeEach
	public void before(){
		pool.init();
	}
	
	@Test
	public void select() throws InterruptedException{
		
		//测试是否释放
		/*for(int i=0;i<20;i++){
			PooledConnection conn = pool.getConnection();
			System.out.println(conn);
			Thread.sleep(1000);
			conn.close();
		}*/
		
		PooledConnection conn = pool.getConnection();
		ResultSet rs = conn.queryBySql("select * from user_info");
		
		try {
			while (rs.next()) {
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
			}
			
			//注意一定要释放，不然会使得数据库池满溢
			rs.close();
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
