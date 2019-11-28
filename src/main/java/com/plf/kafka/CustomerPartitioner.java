package com.plf.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CustomerPartitioner implements Partitioner{

	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public void close() {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		
		return 0;
	}

}
