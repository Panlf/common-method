package com.plf.kafka.stream;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

public class KafkaSteams {
	public static void main(String[] args) {
		//创建配置文件
		Properties props = new Properties();
		props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-streams");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        
		StreamsBuilder builder = new StreamsBuilder();
		
		//从Topic-kafka-streams-data获取数据
		KStream<String, String> source = builder.stream("streams-original-data");
		KStream<String, String> dealSource = source.mapValues(data -> data.replaceAll(">>", ""));
	
		dealSource.to("streams-deal-data");
		
		 //创建和启动KStream
        KafkaStreams kafkaStreams = new KafkaStreams(builder.build(), props);
        kafkaStreams.start();
	}
}
