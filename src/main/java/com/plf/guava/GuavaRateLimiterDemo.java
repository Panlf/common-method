package com.plf.guava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;

/**
 * RateLimiter实现限流
 * 
 * @author plf 2019年12月2日上午11:33:41
 *
 */
public class GuavaRateLimiterDemo {
	private final static Logger log = LoggerFactory.getLogger(GuavaRateLimiterDemo.class);

	//30个线程
	private static final ExecutorService rateLimitPool = Executors.newFixedThreadPool(30);

	//每秒10个令牌
	private static final RateLimiter rateLimiter = RateLimiter.create(10);

	public static void TestRateLimiter() {
		for (int i = 0; i < 100; i++) {
			rateLimitPool.execute(()->{
                rateLimiter.acquire();
                try {
                    log.info(Thread.currentThread().getName()+" take a token");
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
		}
	}
	
	public static void main(String[] args) {
		TestRateLimiter();
	}

}
