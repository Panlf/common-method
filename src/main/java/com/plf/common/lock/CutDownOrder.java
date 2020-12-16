package com.plf.common.lock;

public class CutDownOrder {
	int i = 6000;

	public void cutDown() {
		i--;
	}

	public static void main(String[] args) {
		CutDownOrder cutDownOrder = new CutDownOrder();

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
