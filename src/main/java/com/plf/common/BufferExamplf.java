package com.plf.common;

import org.junit.Test;
/**
 * StringBuffer 和  StringBuilder
 * @author plf 2017年7月6日下午10:17:46
 * StringBuffer：线程安全
 * StringBuilder： 线程不安全
 * 单线程操作：使用StringBuilder效率高
 * 多线程操作：使用StringBuffer安全
 */
public class BufferExamplf {
	@Test
	public void TestStringBuffer(){
		/**
		 * 字符缓冲区
		 * 特点
		 * 1、可以对字符串内容进行修改
		 * 2、是一个容器
		 * 3、是可变长度
		 * 4、缓冲区中可以存储任意类型的数据
		 * 5、最终需要变成字符串
		 */
		String str="plf";
		StringBuffer sb=new StringBuffer(str);
		
		//添加
		sb.append("pcq").append(1314);//在后面增加
		System.out.println(sb.getClass().getTypeName()+":"+sb);
		System.out.println(sb.insert(1,"an"));//指定位置添加
		
		//删除
		sb.delete(1, 2);//包含左边，不包含右边
		System.out.println(sb);//删除指定位置
		System.out.println(sb.deleteCharAt(1));
	
		//修改
		sb.replace(3, 4, "pan");
		System.out.println(sb);
		sb.setCharAt(0, 'P');
		System.out.println(sb);
		//sb.setLength(3);//截取
		System.out.println(sb);
		
		//查找
		System.out.println(sb.indexOf("pan"));
		System.out.println(sb.indexOf("pan",2));
		System.out.println(sb.lastIndexOf("pan"));
		System.out.println(sb.lastIndexOf("pan",2));
		
		//获取子串
		System.out.println(sb.substring(4));
		System.out.println(sb.substring(1, 4));
		
		//反转
		System.out.println(sb.reverse());
	}
	
	@Test
	public void TestStringBuilder(){
		String str="plf";
		StringBuilder sb=new StringBuilder(str);
		
		//添加
		sb.append("pcq").append(1314);//在后面增加
		System.out.println(sb.getClass().getTypeName()+":"+sb);
		System.out.println(sb.insert(1,"an"));//指定位置添加
		
		//删除
		sb.delete(1, 2);//包含左边，不包含右边
		System.out.println(sb);//删除指定位置
		System.out.println(sb.deleteCharAt(1));
	
		//修改
		sb.replace(3, 4, "pan");
		System.out.println(sb);
		sb.setCharAt(0, 'P');
		System.out.println(sb);
		//sb.setLength(3);//截取
		System.out.println(sb);
		
		//查找
		System.out.println(sb.indexOf("pan"));
		System.out.println(sb.indexOf("pan",2));
		System.out.println(sb.lastIndexOf("pan"));
		System.out.println(sb.lastIndexOf("pan",2));
		
		//获取子串
		System.out.println(sb.substring(4));
		System.out.println(sb.substring(1, 4));
		
		//反转
		System.out.println(sb.reverse());
	}
}