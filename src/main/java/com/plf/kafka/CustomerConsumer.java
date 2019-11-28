package com.plf.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class CustomerConsumer {
	
	public static void main(String[] args) {
		Properties props = new Properties();
		
		//设置kafka集群的地址
		props.put("bootstrap.servers", "localhost:9092");
        
        //消费者组ID
		props.put("group.id", "kafka-test-group");
        
        //设置自动提交offset
		props.put("enable.auto.commit", "true");
        //自动提交间隔
		props.put("auto.commit.interval.ms", "1000");
        
        //earliest 
        //当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        //latest
        //当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        //none
        //topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        //默认建议用earliest。设置该参数后 kafka出错后重启，找到未消费的offset可以继续消费。
		props.put("auto.offset.reset", "earliest");
       
        //Consumer session 过期时间
		props.put("session.timeout.ms", "30000");
        
        //反序列化器
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	
	
        //创建消费者对象
        @SuppressWarnings("resource")
		KafkaConsumer<Object, Object> consumer = new KafkaConsumer<>(props);
        
        //指定Topic
        //consumer.subscribe(Arrays.asList("first","second","third"));
        consumer.subscribe(Collections.singletonList("kafka-topic"));
        
        while (true) {
	        //获取数据
	        ConsumerRecords<Object, Object> consumerRecords = consumer.poll(Duration.ofMillis(100));
	        
	        for (ConsumerRecord<Object, Object> consumerRecord : consumerRecords) {
				System.out.println(consumerRecord.topic()+":"
						+consumerRecord.partition()+":"
						+consumerRecord.value());
			}
        }
        
 	}
}
