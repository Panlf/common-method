package com.plf.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * 反射的基本概念
 * @author plf 2017年7月8日下午10:18:59
 *
 */
public class ReflectExample {

	//反射技术：其实就是动态加载一个指定的类，并获取该类中的所有内容
	//简单说：反射技术可以对一个类进行解剖
	/*
	 * 反射的基本步骤：
	 * 1、获得Class对象，就是获取到指定的名称的字节码文件对象
	 * 2、实例化对象，获得类的属性、方法或构造函数
	 * 3、访问属性、调用方法、调用构造函数创建对象
	 */
	@Test
	public void TestReflect(){
		String className="com.plf.common.Person";
		try {
			//获取Class对象
			//1、根据给定的类名获得   用于类加载
			Class<?> clazz=Class.forName(className);
			System.out.println(clazz);//此对象代表Person.class
			
			//2、如果拿到了对象，不知道是什么类型  用于获得对象的类型
			Object obj=new Person();
			Class<?> clazz1=obj.getClass();//获得对象具体的类型
			System.out.println(clazz1);
			
			//3、如果是明确地获得某个类的Class对象   主要用于传参
			Class<?> clazz2 = Person.class;
			System.out.println(clazz2);
			
			//获取类中的所有方法
			Method[] methods=clazz.getMethods();
			methods=clazz.getDeclaredMethods();//获取类中的方法包括私有方法
			for (Method method : methods) {
				System.out.println(method);
			}
			System.out.println("--------------------------");
			
			//用带参数的构造函数新建对象
			Constructor<?> constructor=clazz.getConstructor(String.class,int.class);
			Object obj1=constructor.newInstance("pcq",23);
			//Method m=clazz.getMethod("Say",String.class,int.class);
			//m.invoke(obj1,"",0);
			System.out.println(obj1);
			
			//获取构造函数
			Constructor<?>[] constructors=clazz.getConstructors();
			constructors=clazz.getDeclaredConstructors();
			for (Constructor<?> constructor2 : constructors) {
				System.out.println(constructor2);
			}
			//调用方法
			Method method=clazz.getMethod("Say",String.class,int.class);
			//该实例化对象的方法调用就是指定类中的空参数构造函数，给创建对象进行初始化。
			Object person=clazz.newInstance();
			method.invoke(person,"plf",19);
			
			//运行私有方法，但是不建议使用，因为私有方法本来就是不希望外部使用
//			Method method1=clazz.getDeclaredMethod("Eat",null);
//			method1.setAccessible(true);
//			System.out.println(method1.invoke(person, null));
			
			//静态方法
			//Method method2=clazz.getMethod("Run", null);
			//method2.invoke(null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

class Person{
	private String name;
	private int age;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Person(){}

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	
	public void Say(String name,int age){
		System.out.println("I am "+age+".My name is "+name+"!"+Eat());
	}
	
	private String Eat(){
		return "I eat rice!";
	}
	public static void Run(){
		System.out.println("I can run");
	}
}