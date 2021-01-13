package com.plf.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.function.Predicate;
import java.util.function.ToIntBiFunction;

import org.junit.Test;

public class FunctionInterface {

	@Test
	public void testFunction() {
		//匿名函数方法
		/*Function<String,String> function = new Function<String,String>() {
			@Override
			public String apply(String t) {
				return t.toUpperCase();
			}
		};*/
		//Lambda方式
		Function<String,String> function = t -> t.toUpperCase();
		dealString(Arrays.asList("i","love","you"),function);
	}
	
	@Test
	public void testToIntBiFunction() {
		//匿名函数方法
		/*ToIntBiFunction<Integer,Integer> function = new ToIntBiFunction<Integer,Integer>(){
			@Override
			public int applyAsInt(Integer t, Integer u) {
				return t+u;
			}
		};*/
		//Lambda方式
		ToIntBiFunction<Integer,Integer> function = (t,u) -> t+u;
		dealInteger(Arrays.asList(1,5,7),function);
	}
	
	@Test
	public void testPredicateList() {
		//匿名函数方法
		/*Predicate<Integer> function = new Predicate<Integer>() {
			@Override
			public boolean test(Integer t) {
				return t % 2 == 0;
			}
		};*/
		//Lambda方式
		Predicate<Integer> function = t -> t % 2 == 0;
		dealInteger(Arrays.asList(1,5,6,7),function);
	}
	
	@Test
	public void testCustomFunction() {
		UpperCaseString<String,String> function = (t,u) -> t.toUpperCase()+":"+u.toUpperCase();
		dealString(new HashMap<String,String>(){
			private static final long serialVersionUID = 681644072365744661L;
			{
				put("name","plf");
				put("address","sx");
			}
		},function);
	}
	
	
	public void dealString(List<String> list,Function<String,String> function) {
		for(String s:list) {
			System.out.print(function.apply(s)+" ");
		}
	}
	
	public void dealInteger(List<Integer> list,ToIntBiFunction<Integer,Integer> function) {
		Integer sum=0;
		for(Integer l:list) {
			sum=function.applyAsInt(sum, l);
		}
		System.out.println(sum);
	}
	
	public void dealInteger(List<Integer> list,Predicate<Integer> function) {
		for(Integer i:list) {
			if(function.test(i)) {
				System.out.print(i+" ");
			}
		}
	}
	
	public void dealString(Map<String,String> map,UpperCaseString<String,String> function) {
		for(String key:map.keySet()) {
			System.out.println(function.upperString(key, map.get(key)));
		}
	}
	
	@FunctionalInterface
	public interface UpperCaseString<T,U> {
		public String upperString(String t,String u);
	}	
	
	@Test
	public void testLongFunction() {
		LongFunction<String> function = t -> String.valueOf(t);
		String result = function.apply(12l);
		System.out.println(result);
	}
	
	@Test
	public void testSupplier() {
		LongSupplier supplier = () -> 2l;
		System.out.println(supplier.getAsLong());
	}
	
	@Test
	public void testPredicate() {
		BiPredicate<String, String> predicate = (t,u) -> t.toLowerCase().equals(u.toLowerCase());
		System.out.println(predicate.test("AAb", "aaB"));
	}
	
	@Test
	public void testConsumer() {
		IntConsumer consumer = t -> System.out.println(t+1);
		consumer.accept(2);
	}
	
	@Test
	public void testOperator() {
		BinaryOperator<Integer> binaryOperator = (t,u) -> t+u;
		System.out.println(binaryOperator.apply(2, 3));
		
		System.out.println(BinaryOperator.maxBy(Double::compareTo).apply(2.5, 4.5));
		
		BinaryOperator<String> stringOperator = BinaryOperator.minBy(Comparator.naturalOrder());
		System.out.println(stringOperator.apply("A","B"));
		
		
		BinaryOperator<String> stringOperator2 = BinaryOperator.minBy(String::compareTo);
		System.out.println(stringOperator2.apply("2020-01-14","2020-01-13"));
		
	}
}


