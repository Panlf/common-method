package com.plf.thread;
/**
 * 多个线程访问共享对象和数据的方式
 * @author plf 2018年1月9日下午10:08:40
 *
 */
public class MultiThreadShareData {

	private static ShareData1 data1 = new ShareData1();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final ShareData1 data2=new ShareData1();
		new Thread(new MyRunnable1(data2)).start();
		new Thread(new MyRunnable2(data2)).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				data1.decrement();
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				data1.increment();
			}
		}).start();
	}

}

class MyRunnable1 implements Runnable{

	private ShareData1 data1;
	public MyRunnable1(ShareData1 data1){
		this.data1=data1;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		data1.decrement();
	}
	
}

class MyRunnable2 implements Runnable{

	private ShareData1 data1;
	public MyRunnable2(ShareData1 data1){
		this.data1=data1;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		data1.increment();
	}
	
}
class ShareData1 /*implements Runnable*/{
	/*private int count = 100;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			count--;
		}
	}*/
	private int j=0;
	
	public synchronized void increment(){
		j++;
		System.out.println("increment j="+j);
	}
	public synchronized void decrement(){
		j--;
		System.out.println("decrement j="+j);
	}
}