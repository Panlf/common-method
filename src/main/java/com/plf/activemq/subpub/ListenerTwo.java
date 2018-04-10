package com.plf.activemq.subpub;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ListenerTwo implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			System.out.println("订阅者2-收到的消息："+((TextMessage)message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
