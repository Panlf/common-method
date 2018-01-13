package com.plf.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 
 * Semaphore可以维护当前访问自身的线程个数，并提供了同步机制。使用Semaphore可以控制同时访问
 * 资源的线程个数。
 *	
 * 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是由一个线程获得 了锁，再由另一个线程释放锁，
 * 这可以应用于死锁恢复的场合。
 */
public class SemaphoreTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService service = Executors.newCachedThreadPool();
		final Semaphore sp = new Semaphore(3);
		for(int i=0;i<10;i++){
			Runnable runnable = new Runnable(){
				public void run(){
					try {
						sp.acquire();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					System.out.println("线程"+Thread.currentThread().getName()+"进入，当前已有"+(3-sp.availablePermits())+"个并发");
					try {
						Thread.sleep((long)(Math.random()*10000));
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					System.out.println("线程"+Thread.currentThread().getName()+"即将离开");
					sp.release();
					System.out.println("线程"+Thread.currentThread().getName()+"已离开，当前已有"+(3-sp.availablePermits())+"个并发");
				}
				
			};
			
			service.execute(runnable);
		}
	}		

}
