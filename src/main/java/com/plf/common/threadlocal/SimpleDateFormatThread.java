package com.plf.common.threadlocal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatThread {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<>();

	//第一次get的时候返回null，需要先放入一个SimpleDateFormat
	public DateFormat getDateFormat() {
		DateFormat df = threadLocal.get();
		if (df == null) {
			df = new SimpleDateFormat(DATE_FORMAT);
			threadLocal.set(df);
		}
		return df;
	}
	
	  public static void main(String [] args) {
		  SimpleDateFormatThread formatDemo = new SimpleDateFormatThread();

	        MyRunnable myRunnable1 = new MyRunnable(formatDemo);
	        MyRunnable myRunnable2 = new MyRunnable(formatDemo);
	        MyRunnable myRunnable3 = new MyRunnable(formatDemo);

	        Thread thread1= new Thread(myRunnable1);
	        Thread thread2= new Thread(myRunnable2);
	        Thread thread3= new Thread(myRunnable3);
	        thread1.start();
	        thread2.start();
	        thread3.start();
	    }


	    public static class MyRunnable implements Runnable {

	        private SimpleDateFormatThread simpleDateFormatThread;

	        public MyRunnable(SimpleDateFormatThread simpleDateFormatThread) {
	            this.simpleDateFormatThread = simpleDateFormatThread;
	        }

	        @Override
	        public void run() {
	            System.out.println(Thread.currentThread().getName()+" 当前时间："+simpleDateFormatThread.getDateFormat().format(new Date()));
	        }
	    }

}
