package com.plf.common.reflect;

public class Person {
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

	public Person() {
	}

	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public void Say(String name, int age) {
		System.out.println("I am " + age + ".My name is " + name + "!" + Eat());
	}

	private String Eat() {
		return "I eat rice!";
	}

	public static void Run() {
		System.out.println("I can run");
	}
}
