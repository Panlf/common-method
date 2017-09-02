package com.plf.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailExample {
	private static String sendMail="";
	private static String password="";
	private static String receiveMail="";
	
	public static void main(String[] args) throws UnsupportedEncodingException, MessagingException{
		new MailExample().sendPicAndWordMail(receiveMail);
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
	
	public void sendPicAndWordMail(String target) throws UnsupportedEncodingException, MessagingException{
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
        message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(target, "目标用户", "UTF-8"));//收件邮箱
        message.setSubject("简历邮件");//标题
        
        
        //创建图片节点
        MimeBodyPart img=new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("E:\\temp\\35.jpg")); // 读取本地文件
        img.setDataHandler(dh);
        img.setContentID("img_id");
        
        //创建文本节点
        MimeBodyPart text = new MimeBodyPart();
        text.setContent("这是一张图片<br/><img src='cid:img_id'/>", "text/html;charset=UTF-8");
        
        
        //将文本和图片节点混合
        MimeMultipart mm_text_image = new MimeMultipart();
        mm_text_image.addBodyPart(text);
        mm_text_image.addBodyPart(img);
        mm_text_image.setSubType("related");    // 关联关系
        
        //将 文本+图片 的混合“节点”封装成一个普通“节点”   
        MimeBodyPart text_image = new MimeBodyPart();
        text_image.setContent(mm_text_image);

        //创建文本节点
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler dh2 = new DataHandler(new FileDataSource("E:\\简历\\Java开发.docx"));  // 读取本地文件
        attachment.setDataHandler(dh2);                                     // 将附件数据添加到“节点”
        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));
        
        
        // 设置（文本+图片）和 附件 的关系（合成一个大的混合“节点” / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text_image);
        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed"); 
       
        //将整个内容添加进去
        message.setContent(mm);
        
        message.setSentDate(new Date());//发件时间
        message.saveChanges();//保存
        //获取邮件传输对象
        Transport transport = session.getTransport();
        transport.connect(sendMail, password);
        transport.sendMessage(message, message.getAllRecipients());//获取所有的收件邮箱
        transport.close();//关闭连接
	}
	
}