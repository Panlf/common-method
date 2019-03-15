package com.plf.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于实现两个人的之间的数据交换，每个人在完成一定的事务后想与对方交换数据，
 * 第一个先拿出数据将一直等待第二个人拿着数据到来时，才彼此交换数据
 *
 */
public class ExchangerTest {

	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		final Exchanger<String> exchanger = new Exchanger<String>();
		service .execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String data1="xxs";
					System.out.println("线程"+Thread.currentThread().getName()+"正在把数据"+data1+"换出去");
					Thread.sleep((long)(Math.random()*1000));
					String data2 = exchanger.exchange(data1);
					System.out.println("线程"+Thread.currentThread().getName()+"换回来的数据为"+data2);
				} catch (Exception e) {
				}
			}
		});
		
		
		service .execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					String data1="kky";
					System.out.println("线程"+Thread.currentThread().getName()+"正在把数据"+data1+"换出去");
					Thread.sleep((long)(Math.random()*1000));
					String data2 = exchanger.exchange(data1);
					System.out.println("线程"+Thread.currentThread().getName()+"换回来的数据为"+data2);
				} catch (Exception e) {
				}
			}
		});
	}

}
