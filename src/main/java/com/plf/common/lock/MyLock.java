package com.plf.common.lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class MyLock implements Lock{

	//存放当前线程
	AtomicReference<Thread> owner = new AtomicReference<>();
	//队列 -- 存放哪些没有抢到锁的线程
	LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

	@Override
	public void lock() {
		while(!owner.compareAndSet(null, Thread.currentThread())) {
			// false
			waiters.add(Thread.currentThread());
			//让当前线程阻塞
			LockSupport.park();
			waiters.remove(Thread.currentThread());
		}
	}



	@Override
	public void unlock() {
		//只有持有锁的线程才能解锁
		if(owner.compareAndSet(Thread.currentThread(), null)) {
			//唤醒其他等待的线程
			for(Thread thread:waiters) {
				LockSupport.unpark(thread);
			}
		}else {
			// 失败了，不用做任何操作
		}
	}



	@Override
	public void lockInterruptibly() throws InterruptedException {

	}



	@Override
	public Condition newCondition() {
		return null;
	}



	@Override
	public boolean tryLock() {
		return false;
	}



	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

}
