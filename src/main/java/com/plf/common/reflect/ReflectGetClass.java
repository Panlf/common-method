package com.plf.common.reflect;

public class ReflectGetClass {
	
	public static void main(String[] args) throws ClassNotFoundException {
		// 第一种方式  【在源代码阶段】
		Class<?>  clazz1 = Class.forName("com.plf.common.reflect.Book");
		System.out.println(clazz1.hashCode());
		
		// 第二种方式  【Class对象阶段】
		Class<Book>  clazz2 = Book.class;
		System.out.println(clazz2.hashCode());
		
		// 第三种方式  【对象阶段】
		Book book = new Book();
		Class<? extends Book>  clazz3 = book.getClass();
		System.out.println(clazz3.hashCode());
	}
}
