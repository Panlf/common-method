package com.plf.jdbcpool;

public interface IPool {
	PooledConnection getConnection();
	void createConnections(int count);
}
