package com.plf.kafka;

import java.time.Duration;
import java.util.*;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

/**
 * 指定分区消费
 * @author Panlf
 *
 */
public class ConsumerPartition {
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
		TopicPartition tpc_1_0  = new TopicPartition("topic_1", 0);
		TopicPartition tpc_2_1  = new TopicPartition("topic_2", 1);
		
		kafkaConsumer.assign(Arrays.asList(tpc_1_0,tpc_2_1));
		
		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100000L));
			
			//先处理tpc_1的0号分区的数据
			List<ConsumerRecord<String,String>> records1 = records.records(tpc_1_0);
			for(ConsumerRecord<String,String> rec:records1) {
				//业务处理
				System.out.println(rec);
			}
			
			//再处理tpc_2的1号分区的数据
			List<ConsumerRecord<String,String>> records2 = records.records(tpc_2_1);
			for(ConsumerRecord<String,String> rec:records2) {
				//业务处理
				System.out.println(rec);
			}
		}
	}
}
