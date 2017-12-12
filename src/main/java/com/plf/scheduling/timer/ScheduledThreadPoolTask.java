package com.plf.scheduling.timer;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * JDk1.5建议使用ScheduledThreadPool而舍弃Timer
 * @author plf 2017年12月12日下午10:07:04
 *
 */
public class ScheduledThreadPoolTask {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ScheduledExecutorService scheduledExecutorService = 
					Executors.newScheduledThreadPool(10);
		//只执行一次,5s时延
		/*	scheduledExecutorService.schedule(new Runnable(){

			@Override
			public void run() {
				System.out.println("============="+new Date()+"=============");
			}

		}, 5, TimeUnit.SECONDS);*/
		
		/*	
		
		scheduleWithFixedDelay ：创建一个给定初始延迟的间隔性的任务，之后的下次执行时间是上一次任务
		从执行到结束所需要的时间+给定的间隔时间。这里的间隔时间(delay)是从上次任务执行结束开始算起的。
		
		scheduleAtFixedRate ：创建一个给定初始延迟的间隔性的任务，之后的每次任务执行时间为 初始延迟 + N * delay(间隔)。

		 最大的区别就是 ，scheduleAtFixedRate 不受任务执行时间的影响。
			
		*/
		
		//5s时延，2s执行一次
		/*scheduledExecutorService.scheduleAtFixedRate(new Runnable(){

			@Override
			public void run() {
				System.out.println("============="+new Date()+"=============");
			}

		}, 5, 2, TimeUnit.SECONDS);*/
		
		
		scheduledExecutorService.scheduleWithFixedDelay(new Runnable(){
			@Override
			public void run() {
				System.out.println("============="+new Date()+"=============");
			}
		}, 5, 2, TimeUnit.SECONDS);
	}

}
