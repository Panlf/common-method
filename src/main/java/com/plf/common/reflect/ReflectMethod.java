package com.plf.common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReflectMethod {
	public static void main(String[] args) throws Exception {
		Class<?> clazz =  Class.forName("com.plf.common.reflect.Book");
		
		Constructor<?> con = clazz.getConstructor(Integer.class,String.class);
		
		Object obj = con.newInstance(1,"Java入门到放弃");
		
		//获取方法
		Method sale = clazz.getDeclaredMethod("sale",String.class);
		Method printBook = clazz.getDeclaredMethod("printBook");
		
		//调用方法
		sale.invoke(obj, "GoLang");
		printBook.invoke(obj);
	}
}
