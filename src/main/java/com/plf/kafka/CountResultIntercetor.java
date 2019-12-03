package com.plf.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class CountResultIntercetor implements ProducerInterceptor<String,String>{

	private int successCount = 0;
	private int errorCount = 0;
	
	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public void close() {
		System.out.println("发送成功:"+successCount+"条数据");
		System.out.println("发送失败:"+errorCount+"条数据");
	}

	@Override
	public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
		if(exception == null){
			successCount++;
		}else{
			errorCount++; 
		}
	}

	@Override
	public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
		return record;
	}

}
