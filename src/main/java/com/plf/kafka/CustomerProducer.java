package com.plf.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class CustomerProducer {

	public static void main(String[] args) {
		//http://kafka.apache.org/documentation/#producerconfigs 更多配置可以访问此地址
		//配置信息
		Properties props = new Properties();
		
		//设置kafka集群的地址 -- localhost:9020,localhost:9021,localhost:9022
        props.put("bootstrap.servers", "localhost:9020,localhost:9021,localhost:9022");
        
        //ack模式，all是最慢但最安全的
        // 0  不等待成功返回  
        // 1  等Leader写成功返回  
        //all 等Leader和所有ISR中的Follower写成功返回,all也可以用-1代替
        props.put("acks", "all");
        
        //失败重试次数
        props.put("retries", 0);
        
        //每个分区未发送消息总字节大小（单位：字节），超过设置的值就会提交数据到服务端
        props.put("batch.size", 16384);
        
        //请求的最大字节数，该值要比batch.size大
        //不建议去更改这个值，如果设置不好会导致程序不报错，但消息又没有发送成功
        //props.put("max.request.size",1048576);
        
        //消息在缓冲区保留的时间，超过设置的值就会被提交到服务端
        //数据在缓冲区中保留的时长,0表示立即发送
        //为了减少网络耗时，需要设置这个值，太大可能容易导致缓冲区满，阻塞消费者，太小容易频繁请求服务端
        props.put("linger.ms", 1000);
        
        //整个Producer用到总内存的大小，如果缓冲区满了会提交数据到服务端
        //buffer.memory要大于batch.size，否则会报申请内存不足的错误
        //不要超过物理内存，根据实际情况调整
        props.put("buffer.memory", 33554432);
        //序列化器
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        
        props.put("interceptor.classes", Arrays.asList("com.plf.kafka.TimeIntercetor","com.plf.kafka.CountResultIntercetor"));
        
        //自定义分区
        //props.put("partitioner.class","com.plf.kafka.CustomerPartitioner");
        
        //kafka的幂等性
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"false");
        
        //创建生产者对象
        KafkaProducer<String,String> producer = new KafkaProducer<>(props);
        
        //循环发送消息
        for(int i=10;i<20;i++){
        	producer.send(new ProducerRecord<String, String>("kafka-topic-test", Integer.toString(i)),new Callback() {
				
				@Override
				public void onCompletion(RecordMetadata metadata, Exception exception) {
					if(exception == null){
						System.out.println(metadata.partition()+" - "+metadata.offset());
					}else{
						System.out.println("发送失败");
					}
				}
			});

        }
        //关闭资源
        producer.close();
	}

}
