package com.plf.common.productive.queue;

import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author panlf
 * @date 2021/11/16
 */
public class BlockingQueueTest {

    @Test
    public void blockingQueueTest() throws InterruptedException {
        // 声明一个容量为10的缓存队列
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

        BlockingQueueProducer producer1 = new BlockingQueueProducer(queue,"1");
        BlockingQueueProducer producer2 = new BlockingQueueProducer(queue,"2");
        BlockingQueueProducer producer3 = new BlockingQueueProducer(queue,"3");

        BlockingQueueConsumer consumer = new BlockingQueueConsumer(queue,"0001");
        BlockingQueueConsumer consumer2 = new BlockingQueueConsumer(queue,"0002");

        // 借助Executors
        ExecutorService service = Executors.newCachedThreadPool();
        // 启动线程
        service.execute(producer1);
        service.execute(producer2);
        service.execute(producer3);
        service.execute(consumer);
        service.execute(consumer2);

        // 执行10s
        Thread.sleep(2 * 1000);
        producer1.stop();
        producer2.stop();
        producer3.stop();

        Thread.sleep(1000);
        // 退出Executor
        service.shutdown();
    }
}
