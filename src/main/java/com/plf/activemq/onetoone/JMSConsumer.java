package com.plf.activemq.onetoone;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/*
 * 消息消费者
 */
public class JMSConsumer {
	
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
	
	public static void main(String[] args) {
		//连接工厂
		ConnectionFactory connectionFactory;
		//连接
		Connection connection = null;
		//会话接受或者发送消息的线程
		Session session;
		//消息的目的地
		Destination destination;
		//消息消费者
		MessageConsumer messageConsumer;
		
		//实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);

		
		try {
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			//创建session
			//session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
			
			//创建连接的消息队列
			destination = session.createQueue("FirstQueue1");
			
			messageConsumer = session.createConsumer(destination);
		
			while(true){
				//获取队列中的消息，receive方法是一个主动获取消息的方法，执行一次，拉取一个消息，开发少用
				TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
				
				System.out.println("收到的消息："+textMessage.getText());
				
				//确认消息，发送处理消息确认消息。通知MQ删除对应的消息
				textMessage.acknowledge();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		
	}

}
