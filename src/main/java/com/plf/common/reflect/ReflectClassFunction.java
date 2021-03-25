package com.plf.common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.alibaba.fastjson.JSON;

public class ReflectClassFunction {
	public static void main(String[] args) throws Exception {
		Class<?> clazz =  Class.forName("com.plf.common.reflect.Book");
		
		//得到属性
		//只能获取public修饰的字段
		Field realPrice = clazz.getField("realPrice");
		System.out.println(realPrice);
		
		Field[] fields = clazz.getFields();
		System.out.println(JSON.toJSONString(fields));
		
		//获取全部字段
		Field id = clazz.getDeclaredField("id");
		System.out.println(id);
		
		Field[] declaredFields = clazz.getDeclaredFields();
		System.out.println(JSON.toJSONString(declaredFields));
		
		System.out.println("--------------------");
		
		//得到构造器
		//找到公有的无参构造器
		Constructor<?> con = clazz.getConstructor();
		System.out.println(con);
		//找到公有的，第一个参数为Integer类型，第二参数为String类型的构造器
		Constructor<?> con1 = clazz.getConstructor(Integer.class,String.class);
		System.out.println(con1);
		
		//取出所有公有的构造方法
		clazz.getConstructors();
		//取出无参构造器
		clazz.getDeclaredConstructor();
		//取出所有的构造器
		clazz.getDeclaredConstructors();
		
		System.out.println("--------------------");
		
		//得到方法
		//根据方法名和参数列表及顺序得到公有的方法对象
		Method sale = clazz.getMethod("sale", String.class);
		System.out.println(sale);
		
		//得到所有的公有的方法
		clazz.getMethods();
		//得到所有方法
		clazz.getDeclaredMethods();
		
		System.out.println("--------------------");
		System.out.println(clazz.getName());
		System.out.println(clazz.getSimpleName());
	}
}
