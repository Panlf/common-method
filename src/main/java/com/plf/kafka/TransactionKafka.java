package com.plf.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class TransactionKafka {

	private static final String BROKERS = "localhost:9090";
	private static final String GROUPID = "g0001";
	
	public static void main(String[] args) {
		// 构造一个消费者，去拉取数据
		Properties props_c = new Properties();
		props_c.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BROKERS);
		props_c.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "ealiest");
		props_c.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props_c.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props_c.put(ConsumerConfig.GROUP_ID_CONFIG, GROUPID);
		props_c.put(ConsumerConfig.CLIENT_ID_CONFIG,"c0001");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props_c);
		consumer.subscribe(Arrays.asList("app_log"));
		
		
		//构造一个生产者，用来输出处理结果
		Properties props_p = new Properties();
		props_p.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props_p.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props_p.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "txn000000001");
		props_p.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,true);
		props_p.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKERS);
		
		KafkaProducer<String, String> producer = new KafkaProducer<>(props_p);
	
		//初始化事务
		producer.initTransactions();
		
		//消费者拉取数据
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
		
		// 获取本消费者所被分配到的分区
		Set<TopicPartition> assignment = consumer.assignment();
		
		try {
		
			//开启事务
			producer.beginTransaction();
			
			//构造一个记录分区->偏移量 的hashMap
			HashMap<TopicPartition,OffsetAndMetadata> offsetsMap = new HashMap<>();
			
			for(TopicPartition topicPartition:assignment) {
				
				
				// 获取当前指定分区的数据
				List<ConsumerRecord<String,String>> partitionRecords = records.records(topicPartition);
				
				//做一些业务逻辑处理
				for(ConsumerRecord<String,String> record:partitionRecords) {
					String newValue = record.value().toUpperCase();
					
					ProducerRecord<String, String> resultRecord = new ProducerRecord<>("app_processed",record.key(),newValue);
					
					// 将处理结果写出
					producer.send(resultRecord);
				}
				
				long lastConsumedOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
	
				offsetsMap.put(topicPartition,new OffsetAndMetadata(lastConsumedOffset+1));
			}
			
			//提交偏移量
			producer.sendOffsetsToTransaction(offsetsMap, GROUPID);
			
			//提交事务
			producer.commitTransaction();
		}catch(Exception e) {
			//如果有异常，则放弃事务
			producer.abortTransaction();
		}
		
		producer.close();
		consumer.close();
	}
}
