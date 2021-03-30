package com.plf.java8;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class StringJoinerExample {
	
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,5,6);
		StringJoiner s = new StringJoiner(",");
		list.forEach(i->s.add(String.valueOf(i)));
		System.out.println(s);
		
		StringJoiner s1 = new StringJoiner(",", "[", "]");
		s1.add("ppy");
		s1.add("kky");
        System.out.println(s1);
        
        StringJoiner s2 = new StringJoiner("','", "'", "'");
        s2.add("1").add("2");
        System.out.println(s2);
	}
}
