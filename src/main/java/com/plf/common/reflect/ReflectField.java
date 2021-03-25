package com.plf.common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class ReflectField {
	public static void main(String[] args) throws Exception {
		Class<?> clazz =  Class.forName("com.plf.common.reflect.Book");
		
		Constructor<?> con = clazz.getConstructor();
		
		Object obj = con.newInstance();
		
		Field id = clazz.getDeclaredField("id");
		
		System.out.println(id.getName());
		System.out.println("------------------------");
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field:fields) {
			System.out.println(field.getName());
		}
		System.out.println("------------------------");
		
		//设值
		id.setAccessible(true);
		id.set(obj, 100);
		System.out.println(obj);
		
	}
}
