package com.plf.common.productive.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author panlf
 * @date 2021/11/16
 */
public class BlockingQueueProducer implements Runnable {

    private String name;
    private volatile boolean isRunning = true;

    private BlockingQueue queue;
    private static AtomicInteger count = new AtomicInteger();
    private static final int DEFAULT_RANGE_FOR_SLEEP = 100;

    public BlockingQueueProducer(BlockingQueue queue,String name) {
        this.queue = queue;
        this.name=name;
    }

    public void run() {
        String data = null;
        Random r = new Random();
        try {
            while (isRunning) {
                Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                data = "data:" + count.incrementAndGet();
                System.out.println(this.name+" 生产  " + data);
                if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
                    System.out.println("放入数据失败：" + data);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName()+" 退出生产者线程！");
        }
    }

    public void stop() {
        isRunning = false;
    }
}
