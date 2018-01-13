package com.plf.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier表示大家彼此等待，大家集合好后才开始出发，分散活动后又在指定地点集合碰面
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ExecutorService service = Executors.newCachedThreadPool();
		final CyclicBarrier cb = new CyclicBarrier(3);
		for(int i=0;i<3;i++){
			Runnable runnable = new Runnable(){
				public void run(){
					try {
						Thread.sleep((long)(Math.random()*10000));
						System.out.println("线程"+Thread.currentThread().getName()+
								"即将到达集合地点1，当前已有"+(cb.getNumberWaiting()+1)+"个已经到达，"+
								(cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
						cb.await();
						
						Thread.sleep((long)(Math.random()*10000));
						System.out.println("线程"+Thread.currentThread().getName()+
								"即将到达集合地点2，当前已有"+(cb.getNumberWaiting()+1)+"个已经到达，"+
										(cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
						cb.await();
						
						Thread.sleep((long)(Math.random()*10000));
						System.out.println("线程"+Thread.currentThread().getName()+
								"即将到达集合地点3，当前已有"+(cb.getNumberWaiting()+1)+"个已经到达，"+
										(cb.getNumberWaiting()==2?"都到齐了，继续走啊":"正在等候"));
						cb.await();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
	
				}
				
			};
			
			service.execute(runnable);
		}
		
		service.shutdown();
	}		

}
