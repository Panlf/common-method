package com.plf.common.productive.queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author panlf
 * @date 2021/11/16
 */
public class BlockingQueueConsumer implements Runnable {

    private String name;
    private BlockingQueue<String> queue;
    private static final int DEFAULT_RANGE_FOR_SLEEP = 100;

    public BlockingQueueConsumer(BlockingQueue<String> queue,String name) {
        this.queue = queue;
        this.name = name;
    }

    public void run() {
        Random r = new Random();
        boolean isRunning = true;
        try {
            while (isRunning) {
                String data = queue.poll(2, TimeUnit.SECONDS);
                if (null != data) {
                    System.out.println(this.name+" 消费数据  " + data);
                    Thread.sleep(r.nextInt(DEFAULT_RANGE_FOR_SLEEP));
                } else {
                    // 超过2s还没数据，认为所有生产线程都已经退出，自动退出消费线程。
                    isRunning = false;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(Thread.currentThread().getName()+ " 退出消费者线程！");
        }
    }
}
