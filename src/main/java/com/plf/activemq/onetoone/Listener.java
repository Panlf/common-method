package com.plf.activemq.onetoone;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


//注册监听器。注册成功后，队列中的消息变化会自动触发监听器代码。接收消息并处理
public class Listener implements MessageListener {

	/***
	 * 监听器一旦注册，永久有效
	 * 永久 -- consumer线程不关闭
	 * 处理消息的方式，只要有消息未处理，自动调用onMessage方法，处理消息
	 * 监听器可以注册若干，注册多个监听器，相当于集群
	 * ActiveMQ自动的循环调用多个监听器，处理队列中的消息，实现并行处理
	 * 
	 * 
	 */
	@Override
	public void onMessage(Message message) {
		try {
			
			//acknowledge方法就是确认方法。代表consumer已经收到消息。确认后，MQ删除对应的消息
			message.acknowledge();
			
			
			System.out.println("监听模式--收到的消息："+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
