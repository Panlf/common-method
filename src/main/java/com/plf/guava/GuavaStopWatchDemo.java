package com.plf.guava;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * Guava的代码计时器
 * @author plf 2019年12月2日上午9:47:52
 *
 */
public class GuavaStopWatchDemo {

	private final static Logger log = LoggerFactory.getLogger(GuavaStopWatchDemo.class);
	
	@Test
	public void testStopWatch(){
		log.info("StopWatch Test Start...");
		Stopwatch stopWatch = Stopwatch.createStarted();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("StopWatch Test End...cost {} ",stopWatch.stop());
	}
}
