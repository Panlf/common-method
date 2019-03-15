package com.plf.thread;

/**
 * 子线程循环10次，接着主线程循环100，
 * 接着又回到子线程循环10次，接着循环100，这样循环50次
 * @author plf 2018年1月8日下午7:52:26
 *
 */
public class TraditionalThreadCommunication {
	public static void main(String[] args) {
		final Business business= new Business();
		
		new Thread(
			new Runnable() {
				@Override
				public void run() {
					for(int i=1;i<=50;i++){
						business.sub(i);
					}
				}
		}).start();
	
		for(int i=1;i<=50;i++){
			business.main(i);
		}
	}
	
}

class Business{
	private boolean bShouldSub = true;
	public synchronized void sub(int i){
		if(!bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int j=1;j<=10;j++){
			System.out.println("sub thread squece of "+j+",loop of "+i);
		}
		bShouldSub = false;
		this.notify();
	}
	
	public synchronized void main(int i){
		if(bShouldSub){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for(int j=1;j<=100;j++){
			System.out.println("main thread squece of "+j+",loop of "+i);
		}
		bShouldSub = true;
		this.notify();
	}
}
