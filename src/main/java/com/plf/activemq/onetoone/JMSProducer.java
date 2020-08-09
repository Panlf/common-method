package com.plf.activemq.onetoone;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

//消息生产者
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
			//	通过连接工厂获取连接
			//  创建连接的方法有重载	其中有createConnection(String userName, String password)
			//	可以在创建连接工厂时，只传递连接地址，不传递用户信息
			connection = connectionFactory.createConnection();
			//	建议启动连接，消息的发送者不是必须启动连接，消息的消费者必须启动连接
			// producer在发送消息的时候，会检查是否启动了链接。如果未启动，自动启动
			// 如果有特殊的配置，建议配置完毕后再启动链接
			connection.start();
			
			
			//通过连接对象，创建会话对象
			
			/**
			 * 创建会话的时候，必须传递两个参数，分别代表是否支持事务和如何确认消息处理
			 * 	transacted 是否支持事务	true 支持		false 不支持
			 * 		true 支持事务 第二个参数对producer来说默认无效。建议传递的数据是Session.SESSION_TRANSACTED
			 * 		false 不支持事务，常用参数。第二个参数必须传递，且必须有效
			 * 
			 * acknowledgeMode 如何确认消息的处理。使用确认机制实现的
			 * 	AUTO_ACKNOWLEDGE 当客户成功的从receive方法返回时，或者从MessageListener.onMessage方法成功返回时，会自动确认客户收到的消息
			 * 						消息的消费者处理消息后，自动确认，常用。商业开发不推荐	
			 *  CLIENT_ACKNOWLEDGE  客户通过消息的acknowledge方法确认消息，客户端手动确认。消息的消费者处理后，必须手动确认
			 *  
			 *  DUPS_ACKNOWLEDGE 有副本的客户端手动确认
			 *  		一个消息可以多次处理
			 *  		可以降低Session的消耗，在可以容忍重复消息时使用（不推荐使用）
			 * */
			//session = connection.createSession(true,Session.AUTO_ACKNOWLEDGE);
			session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
			
			//创建消息队列	目的地     参数的目的地名称，是目的地的唯一标记
			destination = session.createQueue("FirstQueue1");
			
			//通过会话对象，创建消息的发送者producer
			//创建消息生产者 发送的消息一定到指定的目的地中
			//创建producer的时候，可以不提供目的地，在发送消息的时候指定目的地
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
			//创建文本消息对象，作为具体数据内容的载体
			TextMessage textMessage=session.createTextMessage("ActiveMQ 发送的消息："+i);
			System.out.println("发送消息："+"ActiveMQ 发送的消息："+i);
			//使用producer发送消息到ActiveMQ中的目的地，如果消息发送失败。抛出异常
			messageProducer.send(textMessage);
			
			//deliveryMode -- 持久化方式
			//非持久化 DeliveryMode.NON_PERSISTENT
			//持久化 DeliveryMode.PERSISTENT
		}
	}
	
}
