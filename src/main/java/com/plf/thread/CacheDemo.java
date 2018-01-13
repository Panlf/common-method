package com.plf.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheDemo {

	private Map<String,Object> cache = new HashMap<String,Object> ();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	private ReadWriteLock rwl = new ReentrantReadWriteLock();
	public Object getData(String key){
		rwl.readLock().lock();
		Object value=null;
		try {
			value = cache.get(key);
			if(value == null){
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					if(value == null){
						value = "AAAA"; //实际是访问数据库
					}
				} finally {
					// TODO: handle finally clause
					rwl.writeLock().unlock();
				}	
				rwl.readLock().lock();
			}	
		} finally {
			// TODO: handle finally clause
			rwl.readLock().unlock();
		}
		
		return value;
	}

}
