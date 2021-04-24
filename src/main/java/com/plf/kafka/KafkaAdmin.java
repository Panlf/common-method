package com.plf.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;

public class KafkaAdmin {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Properties props = new Properties();
		props.setProperty(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9090");
		
		AdminClient adminClient = KafkaAdminClient.create(props);
		
		ListTopicsResult listTopicsResult = adminClient.listTopics();
		
		KafkaFuture<Set<String>> names = listTopicsResult.names();
		
		Set<String> topicNames = names.get();
		
		System.out.println(topicNames);
		
		
		
		DescribeTopicsResult tpc_1 = adminClient.describeTopics(Arrays.asList("tpc_1"));
		KafkaFuture<Map<String,TopicDescription>> future = tpc_1.all();
		//get 会阻塞到返回结果为止
		Map<String,TopicDescription> stringTopicDescriptionMap = future.get();
		
		Set<Map.Entry<String,TopicDescription>> entries = stringTopicDescriptionMap.entrySet(); 
		
		for(Map.Entry<String,TopicDescription> entry:entries) {
			System.out.println(entry.getKey());
			TopicDescription desc = entry.getValue();
			System.out.println(desc.name()+","+desc.partitions());
		}
		
		HashMap<Integer,List<Integer>> partitions = new HashMap<>();
		partitions.put(0,Arrays.asList(0,2));
		partitions.put(1,Arrays.asList(1,2));
		partitions.put(2,Arrays.asList(0,1));
		
		NewTopic tpc_2 = new NewTopic("tpc_2",partitions);
		adminClient.createTopics(Arrays.asList(tpc_2));
		
		adminClient.close();
	}

}
