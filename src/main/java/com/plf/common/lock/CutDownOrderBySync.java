package com.plf.common.lock;

public class CutDownOrderBySync {
	int i = 6000;

	public void cutDown() {
		synchronized (CutDownOrderBySync.class) {
			i--;
		}
	}

	public static void main(String[] args) {
		CutDownOrderBySync cutDownOrder = new CutDownOrderBySync();

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
