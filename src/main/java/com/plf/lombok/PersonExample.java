package com.plf.lombok;

public class PersonExample {

	public static void main(String[] args) {
		Person person=new Person("KKY",18,"Male");
		System.out.println(person.toString());
		
		
		Person personBuilder=Person.builder().age(16).name("JYY").sex("Female").build();
		System.out.println(personBuilder);
	}

}
