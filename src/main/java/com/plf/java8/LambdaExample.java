package com.plf.java8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * Lambda的实例
 * @author plf 2017年6月15日上午11:17:57
 *
 */
public class LambdaExample {
/**
 * lambda的语法
 * 1、参数列表
 * 2、箭头符号"->"
 * 3、代码块
 */
	
	//用lambda简化Runnable接口的实现
	@Test
	public void SimplifyRunnable(){
		new Runnable() {
			public void run() {
				System.out.println("匿名内部类实现Runnable接口");
			}
		}.run();
		int i=1;
		Runnable r=()->{
			System.out.println("用lambda实现Runnable接口");
			//i++;这是错误的，不允许修改外部变量的值
			System.out.println("i="+i);
		};
		r.run();
	}
	
	
	//lambda实现自定义接口，模拟登陆操作
	@Test
	public void interfacelambda(){
		new Action(){
			public void execute(String content){
				System.out.println(content);
			}
		}.execute("jdk1.8之前的匿名内部类实现方式，执行登陆操作");
		
		Action login=(String content)->{
			System.out.println(content);
		};
		login.execute("jdk1.8的lambda语法实现登陆");
	}
	static interface Action{
		void execute(String content);
	}
	
	@Test
	public void NewLambda(){
		List<String> idList=new ArrayList<String>();
		idList.add("1");
		idList.add("4");
		idList.add("2");
		idList.add("3");
		idList.forEach(x->System.out.println(x));
		
		Mysort(idList);
		idList.forEach(System.out::println);
		
		String ids=String.join(",", idList);
		System.out.println(ids);
	}
	
	
	public static void Mysort(List<String> id){
		Collections.sort(id,(o1,o2)->o1.compareTo(o2));
	}
}
