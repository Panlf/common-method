package com.plf.java8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	
	private String name;
	/**
	 * 0 Female
	 * 1 male
	 */
	private Integer gender;
	private int age;
	private double height;
}
