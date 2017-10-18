package com.plf.lombok;

public class PersonExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person person=new Person("KKy",18,"female");
		System.out.println("My name is "+person.getName()
		+",my age is "+person.getAge()
		+",my sex is "+person.getSex()+"!");
	}

}
