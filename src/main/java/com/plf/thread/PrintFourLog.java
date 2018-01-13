package com.plf.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 四条线程打印十六条日志数据
 *
 */
public class PrintFourLog {

	public static void main(String[] args) {
		final BlockingQueue<String> queue = new ArrayBlockingQueue<String>(16);//1
		
		// TODO Auto-generated method stub
		for(int i=0;i<4;i++){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(true){
						try {
							String log = queue.take();
							parseLog(log);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
		
		System.out.println("begin:"+(System.currentTimeMillis())/1000);
		
		for(int i=0;i<16;i++){
			final String log = ""+(i+1);
			{
				try {
					queue.put(log);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
			
	}

	public static  void parseLog(String log){
		System.out.println(log+":"+(System.currentTimeMillis()));
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
