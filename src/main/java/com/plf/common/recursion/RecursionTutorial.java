package com.plf.common.recursion;

import java.util.Arrays;
import java.util.List;

public class RecursionTutorial {

	public static void main(String[] args) {
		RecursionTutorial recursion = new RecursionTutorial();
		int result= recursion.sumRecursion(Arrays.asList(1,3,5,2,6));
		System.out.println(result);
	}
	
	//递归相加
	public int sumRecursion(List<Integer> list) {
		if(list.size()==0) {
			return 0;
		}else {
			return list.get(list.size()-1)+sumRecursion(list.subList(0, list.size()-1));
		}
	}
}
