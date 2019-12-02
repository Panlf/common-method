package com.plf.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class TimeIntercetor implements ProducerInterceptor<String,String> {

	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public void close() {
	
	}

	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
	}

	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String,String> record) {
		return new ProducerRecord<String, String>(record.topic(), record.value(),System.currentTimeMillis()+":"+record.value());
	}

}
