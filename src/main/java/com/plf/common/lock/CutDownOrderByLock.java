package com.plf.common.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CutDownOrderByLock {
	int i = 6000;

	Lock lock = new ReentrantLock();
	
	public void cutDown() {
		lock.lock();
		try {
			i--;
		}finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		CutDownOrderByLock cutDownOrder = new CutDownOrderByLock();

		for (int j = 0; j < 6; j++) {
			new Thread(() -> {
				for (int k = 0; k < 1000; k++) {
					cutDownOrder.cutDown();
				}
			}).start();
		}

		try {
			// 等待线程跑完
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("库存量:" + cutDownOrder.i);
	}
}
