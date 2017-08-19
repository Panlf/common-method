package com.plf.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailExample {
	private static String sendMail="";
	private static String password="";
	private static String receiveMail="";
	
	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException{
		new MailExample().sendMail(receiveMail, "您好，我只是测试一下");
	}
	public void sendMail(String target,String context) throws UnsupportedEncodingException, MessagingException{
		Properties props=new Properties();
		props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", "smtp.163.com");   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        //参数配置
        Session session = Session.getDefaultInstance(props);
        //session.setDebug(true); //javax.mail-api的jar包里不提供调试的，需要javax.mail的，不过maven repository没找到
        //创建邮件的对象
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "枫叶梨花", "UTF-8"));//发件邮箱
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(target, "目标用户", "UTF-8"));//收件邮箱
        message.setSubject("测试邮件");//标题
        message.setContent(context,"text/html;charset=UTF-8");//正文
        message.setSentDate(new Date());//发件时间
        message.saveChanges();//保存
        //获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(sendMail, password);
        transport.sendMessage(message, message.getAllRecipients());//获取所有的收件邮箱
        transport.close();//关闭连接
	}
}
