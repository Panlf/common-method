package com.plf.common.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

/**
 * 消费者依次拿到数据
 *
 */
public class ConsumeByOrder {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(1);

		final SynchronousQueue<String> queue = new SynchronousQueue<String>();

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						semaphore.acquire();
						String input = queue.take();
						String output = TestDo.doSome(input);
						System.out.println(Thread.currentThread().getName() + ":" + output);
						semaphore.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}

		System.out.println("begin:" + (System.currentTimeMillis()) / 1000);

		for (int i = 0; i < 10; i++) {
			String input = "" + i;

			try {
				queue.put(input);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class TestDo {
	public static String doSome(String input) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String output = input + ":" + (System.currentTimeMillis() / 1000);
		return output;
	}
}
