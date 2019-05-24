package com.plf.zookeeper.zkclient;

import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
/**
 * 使用Apache Curator的分布式锁
 * 
 * 运行这个类需要注释Zookeeper的相关jar包
 * 只留下curator-recipes的包
 * @author plf 2019年5月24日下午2:18:56
 *
 */
public class Ticket {
	static InterProcessMutex mutex = null;
	// 100张票
	private int count = 100;
	
	static{
		//1、重试策略：初试时间为1s 重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3); 
        //2、通过工厂创建连接
        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        //3、开启连接
        client.start();
        //4 分布式锁
        mutex = new InterProcessMutex(client, "/zkLock"); 
        //读写锁
        //InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, "/readwriter");
	}

	@Test
	public void ticketTest() {
		
		TicketRunnable tr = new TicketRunnable();
		Thread t1 = new Thread(tr, "窗口A");
		Thread t2 = new Thread(tr, "窗口B");
		Thread t3 = new Thread(tr, "窗口C");
		Thread t4 = new Thread(tr, "窗口D");
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public class TicketRunnable implements Runnable {

		@Override
		public void run() {
			boolean flag =true;
			while (count > 0) {
				try {
					flag = mutex.acquire(5, TimeUnit.SECONDS);
					if(flag){
						if (count > 0) {
							System.out.println(Thread.currentThread().getName() + "售出第" + (count--) + "张票");
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}finally {
					if(flag){
                        try {
                            mutex.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
				}
				
			}
		}

	}
}
