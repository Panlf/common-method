package com.plf.activemq.subpub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

//消息发布者
public class JMSProducer {

	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//默认用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认密码
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
	private static final int SENDNUM =10;//发送的消息数量
	public static void main(String[] args) {
		//连接工厂
		ConnectionFactory connectionFactory;
		//连接
		Connection connection = null;
		//会话接受或者发送消息的线程
		Session session;
		//消息的目的地
		Destination destination;
		//消息生产者
		MessageProducer messageProducer;
		
		//实例化连接工厂
		connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
	
		
		try {
			//通过连接工厂获取连接
			connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			
			//true启动事务
			//AUTO_ACKNOWLEDGE 当客户成功的从receive方法返回时，或者从MessageListener.onMessage方法成功返回时，会自动确认客户收到的消息
			//CLIENT_ACKNOWLEDGE 客户通过消息的acknowledge方法确认消息
			//DUPS_ACKNOWLEDGE 该选择只是会话迟钝等确认消息的提交
			session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		
			//创建消息队列
			destination = session.createTopic("FirstTopic1");
			//创建消息生产者
			messageProducer=session.createProducer(destination);
			//发送消息
			sendMessage(session,messageProducer);
			
			session.commit();
			
			
		} catch (JMSException e) {
			e.printStackTrace();
		}finally {
			if(connection!=null){
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void sendMessage(Session session,MessageProducer messageProducer) throws JMSException{
		for(int i=1;i<SENDNUM;i++){
			TextMessage textMessage=session.createTextMessage("ActiveMQ 发送的消息："+i);
			System.out.println("发布者--发送消息："+"ActiveMQ 发送的消息："+i);
			messageProducer.send(textMessage);
		}
	}
	
}
