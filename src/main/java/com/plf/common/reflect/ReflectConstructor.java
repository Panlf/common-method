package com.plf.common.reflect;

import java.lang.reflect.Constructor;

public class ReflectConstructor {
	
	public static void main(String[] args) throws Exception {
		Class<?> clazz =  Class.forName("com.plf.common.reflect.Book");
		
		//得到无参构造器
		Constructor<?> con = clazz.getDeclaredConstructor();
		//打破访问权限 比如能访问到private修饰的无参构造器
		//con.setAccessible(true);
		//调用构造方法
		Object obj = con.newInstance();
		System.out.println(obj);
		
		
		Constructor<?> con1 = clazz.getConstructor(Integer.class,String.class);
		obj = con1.newInstance(1,"Java");
		System.out.println(obj);
		
		
		Class<?>[] parameterTypes = con1.getParameterTypes();
		for(Class<?> parameterType:parameterTypes) {
			System.out.println(parameterType.getName());
		}
	}
}
