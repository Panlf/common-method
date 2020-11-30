package com.plf.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	
	private String name;
	private Integer gender;
	private int age;
	private double height;
}
