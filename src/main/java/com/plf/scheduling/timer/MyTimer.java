package com.plf.scheduling.timer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;

public class MyTimer {
	
	//管理并发任务的缺陷
	//Timer有且只有一个线程去执行定时任务，如果存在多个任务，且任务时间过长，会导致执行效果与预期不符
	//如果TimerTask抛出RuntimeException。Timer会停止所有任务的运行
	//对时效性要求较高的多任务并发作业
	//对复杂的任务调度
	
	/*1、首次计划执行时间早于当前时间
	schedule ： 如果第一次时间被delay了，随后的执行时间按照
				上一次实际执行完成的时间点进行计算
	scheduleAtFixedRate： 如果第一次时间被delay了，随后的执行时间按照
				上一次开始的时间点进行计算，并且为了赶上进度会多次执行任务，
				因此TimerTask中的执行体需要考虑同步
				
	2、任务执行时间超出执行周期间隔
	schedule ：下一次执行时间相对于上一次实际执行完成的时间点，因此执行时间会不断延后
	scheduleAtFixedRate：下一次执行时间相对于上一次开始的时间点，因此执行时间一般不会延后，
			因此存在并发性
	
	*/
	public static void main(String[] args){
		test();
		//TestScheduling();
		//testScheduleAtFixedRate();
	}
	public static void test() {
		//1、新建一个timer实例
		Timer timer=new Timer();
		//2、创建一个MyTimerTask实例
		MyTimerTask myTimerTask = new MyTimerTask("plf");
		//3、设置timer定时定频率调用myTimerTask的业务逻辑
		timer.schedule(myTimerTask, 2000L,1000L);//在程序启动后2s后调用，之后每隔1s调用
	
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//取消myTimerTask的任务
		myTimerTask.cancel();
		//返回取消的任务数，并从队列中移除
		System.out.println(timer.purge());
		
		//取消所有任务
		//timer.cancel();
	}
	
	//schedule(task,time)
	public static void TestScheduling(){
		//1、新建一个timer实例
		Timer timer=new Timer();
		//2、创建一个MyTimerTask实例
		MyTimerTask myTimerTask = new MyTimerTask();
				
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=formatter.format(calendar.getTime());
		System.out.println("当前时间："+now);
		calendar.add(Calendar.SECOND, 3);
		myTimerTask.setInputname("TestScheduling");
		//在时间等于或者超过time的时候执行且只执行一次task
		timer.schedule(myTimerTask,calendar.getTime());
		
		//返回此任务最近实际执行的已安排执行的时间
		System.out.println(formatter.format(myTimerTask.scheduledExecutionTime()));
		//schedule(task,time,period)
		//在时间等于或者超过time的时候执行且只执行一次task 之后没period执行一次任务
		
		//schedule(task,delay)
		//在等待delay毫秒之后，执行且只执行一次
		
		//schedule(task,delay,period)
		//延迟delay毫秒之后执行，之后每period执行一次
	}
	
	public static void testScheduleAtFixedRate(){
		//1、新建一个timer实例
		Timer timer=new Timer();
		//2、创建一个MyTimerTask实例
		MyTimerTask myTimerTask = new MyTimerTask("testScheduleAtFixedRate");
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now=formatter.format(calendar.getTime());
		System.out.println("当前时间："+now);
		calendar.add(Calendar.SECOND, 3);
		
		//scheduleAtFixedRate(task,firsttime,period)
		timer.scheduleAtFixedRate(myTimerTask, calendar.getTime(), 2000L);
			
		//scheduleAtFixedRate(task,delay,period)
	}
}