package com.plf.scheduling.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MyJob implements Job {

	public MyJob() {
     
	}
	
	@Override
	public void execute(JobExecutionContext job) throws JobExecutionException {
		 System.out.println("Hello World!  MyJob is executing.");
	}

}
