package com.plf.scheduling.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class MyScheduler {

	public static void main(String[] args) throws SchedulerException {
	 
	  Scheduler sched  = StdSchedulerFactory.getDefaultScheduler();
	  sched.start();
	  
	  JobDetail job = JobBuilder.newJob(MyJob.class)
		      .withIdentity("myJob", "group1")
		      .build();
	  
	  Trigger trigger = TriggerBuilder.newTrigger()
	      .withIdentity("myTrigger", "group1")
	      .startNow()
	      .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(5))
	      .build();

	  // Tell quartz to schedule the job using our trigger
	  sched.scheduleJob(job, trigger);
	}

}
