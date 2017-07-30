package com.plf.scheduling.timer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

/**
 * Timer 定时任务调度
 * @author plf 2017年7月30日下午2:59:40
 *
 */
public class MyTimerTask extends TimerTask{

	private String inputname;
	private Integer count=0;
	public MyTimerTask(String inputname) {
		super();
		this.inputname = inputname;
	}
	
	public MyTimerTask() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getInputname() {
		return inputname;
	}

	public void setInputname(String inputname) {
		this.inputname = inputname;
	}

	@Override
	public void run() {
		if(count>3){
			cancel();
			System.out.println("任务已经取消");
		}
		LocalDateTime local=LocalDateTime.now();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String now=formatter.format(local);
		System.out.println("输入的是："+inputname+"---  执行任务时间"+now);
		count++;
	}

}
