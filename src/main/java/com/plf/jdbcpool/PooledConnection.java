package com.plf.jdbcpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 被连接池管理的管道信息
 */
public class PooledConnection {
	private Connection conn = null;
	
	private boolean isBusy = false;

	public PooledConnection(Connection conn, boolean isBusy) {
		super();
		this.conn = conn;
		this.isBusy = isBusy;
	}
	
	public void close(){
		this.isBusy = false;
	}
	
	public ResultSet queryBySql(String sql){
		PreparedStatement ps =null;
		ResultSet rs = null;
		try {
			ps=conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	@Override
	public String toString() {
		return "PooledConnection [conn=" + conn + ", isBusy=" + isBusy + "]";
	}
}

