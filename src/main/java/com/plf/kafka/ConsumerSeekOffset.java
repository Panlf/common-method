package com.plf.kafka;

import java.time.Duration;
import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

public class ConsumerSeekOffset {
	private static final String SERVERS = "localhost:9020,localhost:9021,localhost:9022";
	
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class.getName());
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,SERVERS);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,true);
		props.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
		
	
		@SuppressWarnings("resource")
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(props);
		
		kafkaConsumer.subscribe(Arrays.asList("tpc_1"));
		kafkaConsumer.poll(Duration.ofMillis(100000));
		
		Set<TopicPartition> assignment = kafkaConsumer.assignment();
		
		for(TopicPartition topicPartition:assignment) {
			kafkaConsumer.seek(topicPartition, 1000L);
		}
		
		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(Long.MAX_VALUE));
			for(ConsumerRecord<String,String> record:records) {
				//业务处理
				System.out.println(record);
			}
		}
	
	}
}
