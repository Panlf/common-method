package com.plf.guava;

import java.util.List;
import java.util.Objects;

import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * Preconditions检验参数
 * @author plf 2019年12月2日上午11:21:52
 *
 */
public class GuavaPreconditionsDemo {
	@Test(expected = NullPointerException.class)
	public void testCheckNotNull(){
		Preconditions.checkNotNull(null);
	}
	
	@Test
	public void testCheckNotNullWithMessage(){
		try{
			Preconditions.checkNotNull(null,"这个参数不能为null");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	@Test
	public void testCheckArguments(){
		try{
			Preconditions.checkArgument("A".equals("B"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCheckState(){
		try{
			Preconditions.checkState("A".equals("B"),"The state is not A");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCheckIndex(){
		try{
			List<String> list = ImmutableList.of();
			Preconditions.checkElementIndex(10, list.size());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testByObjects(){
		Objects.requireNonNull(null);
	}
}
