package com.plf.redis;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import redis.clients.jedis.Jedis;

public class RedisDistributedLock implements Lock {

	// 保存当前锁的持有人
	private ThreadLocal<String> lockContext = new ThreadLocal<String>();

	// 默认锁的超时时间
	private long time = 100;

	private Thread exclusiveOwnerThread;

	public RedisDistributedLock() {
	}

	static Jedis jedis = null;
	static {
		jedis = new Jedis("127.0.0.1", 6379);
	}

	@Override
	public void lock() {
		while (!tryLock()) {
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public boolean tryLock() {
		return tryLock(time, TimeUnit.MILLISECONDS);
	}

	public boolean tryLock(long time, TimeUnit unit) {
		// 唯一分配ID
		String id = UUID.randomUUID().toString();
		Thread t = Thread.currentThread();
		if (jedis.setnx("lock", id) == 1) {
			// 设置锁过期时间
			jedis.pexpire("lock", unit.toMillis(time));
			// 记录持有人id
			lockContext.set(id);
			// 记录当前线程
			setExclusiveOwnerThread(t);
			return true;
		} else if (exclusiveOwnerThread == t) { // 当前线程已经获得锁，可重入
			return true;
		}
		return false;
	}

	private void setExclusiveOwnerThread(Thread t) {
		this.exclusiveOwnerThread = t;
	}

	@Override
	public void unlock() {
		String script = null;
		try {
			script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then return redis.call(\"del\",KEYS[1]) else return 0 end";
			if (lockContext.get() == null) {
				return;
			}
			// 删除锁
			jedis.eval(script, Arrays.asList("lock"), Arrays.asList(lockContext.get()));
			lockContext.remove();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
		while (!tryLock()) {
			Thread.sleep(100);
		}
	}

	@Override
	public Condition newCondition() {
		return null;
	}

}
