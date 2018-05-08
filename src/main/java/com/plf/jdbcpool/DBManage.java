package com.plf.jdbcpool;

public class DBManage {
	private static class CreatePool{
		private static JdbcPool pool = new JdbcPool();
	}
	
	public static JdbcPool getInstance(){
		return CreatePool.pool;
	}
}
