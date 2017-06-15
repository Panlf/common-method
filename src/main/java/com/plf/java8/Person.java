package com.plf.java8;

public class Person {
	
	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Person(String name, Sex gender, int age, double height) {
		super();
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return "Person [name=" + name + ", gender=" + gender + ", age=" + age + ", height=" + height + "]";
	}

	public static enum Sex{
		MALE,FEMALE;
	}
	private String name;
	private Sex gender;
	private int age;
	private double height;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getGender() {
		return gender;
	}
	public void setGender(Sex gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
}
