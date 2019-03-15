package com.plf.thread;

public class TraditionalThread {
	public static void main(String[] args) {
		//查看Thread类的run()方法的源代码，可以看到其实这两种方式
		//都是在调用Thread对象的run方法，如果Thread类的run方法没有覆盖，
		//并且为该Thread对象设置了一个Runnable对象，该run方法会调用Runnable对象的run方法
		Thread thread = new Thread(){
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("1:"+Thread.currentThread().getName());
					System.out.println("2:"+this.getName());
				}
		}};
		thread.start();
		
		
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("1:"+Thread.currentThread().getName());
				}
			}
		});
		thread2.start();
	}
}
