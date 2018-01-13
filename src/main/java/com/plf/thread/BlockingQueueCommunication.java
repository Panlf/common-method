package com.plf.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用两个具有1个空间的队列实现同步通知的功能
 *
 */
public class BlockingQueueCommunication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final Business business = new Business();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<=50;i++){
					business.sub(i);
				}
			}
		}).start();
		
		for(int i=1;i<=50;i++){
			business.main(i);
		}
	}

	static class Business{
		BlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(1);
		BlockingQueue<Integer> queue2 = new ArrayBlockingQueue<Integer>(1);
		{
			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void sub(int i){
			try {
				queue1.put(1);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			for(int j=1;j<=10;j++){
				System.out.println("sub thread sequeue of "+j+",loop of "+i);
			}
			
			try {
				queue2.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void main(int i){
			try {
				queue2.put(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(int j=1;j<=100;j++){
				System.out.println("sub thread sequeue of "+j+",loop of "+i);
			}
			
			try {
				queue1.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
