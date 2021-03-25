package com.plf.common.reflect;

public class Book {

	private Integer id;
	
	private String name;
	
	double price;
	
	protected String author;
	
	public double realPrice;

	
	public Book() {
		System.out.println("构造方法被调用");
	}
	

	public Book(Integer id,String name){
		this.id = id;
		this.name = name;
	}
	
	
	public void sale(String bookName) {
		System.out.println("卖书的名称="+bookName);
	}
	
	public void printBook() {
		System.out.println("Print Book id = "+id+" ,name = "+name);
	}
	
	@Override
	public String toString() {
		return "Book  id = "+id+" ,name = "+name;
	}
}
