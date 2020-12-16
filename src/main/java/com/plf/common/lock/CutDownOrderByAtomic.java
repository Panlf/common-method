package com.plf.common.lock;

import java.util.concurrent.atomic.AtomicInteger;

public class CutDownOrderByAtomic {
	AtomicInteger atomicInteger = new AtomicInteger(6000);
	
	public void cutDown() {
		atomicInteger.decrementAndGet();
	}

	public static void main(String[] args) {
		CutDownOrderByAtomic cutDownOrder = new CutDownOrderByAtomic();

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

		System.out.println("库存量:" + cutDownOrder.atomicInteger.get());
	}
}
