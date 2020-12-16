package com.plf.common.lock;

import java.util.concurrent.locks.Lock;

public class CutDownOrderByMyLock {
	int i = 6000;

	Lock lock = new MyLock();
	
	public void cutDown() {
		lock.lock();
		try {
			i--;
		}finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		CutDownOrderByMyLock cutDownOrder = new CutDownOrderByMyLock();

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
