package com.plf.java8;

import java.util.Optional;

import org.junit.Test;

public class OptionalExample {
	@Test
	public void TestOptional(){
		//of为非null的值创建一个Optional。
		//为指定的值创建一个Optional，如果指定的值为null，则返回一个空的Optional。
		
		//ofNullable与of方法相似，唯一的区别是可以接受参数为null的情况
		Optional<String> name=Optional.of("plf");
		Optional<String> empty=Optional.ofNullable(null);
		System.out.println(name+""+empty);
		
		//isPresent 如果值存在返回true，否则返回false
		//get 如果Optional有值则将其返回，否则抛出NoSuchElementException。
		if(name.isPresent()){
			System.out.println(name.get());
		}
		
		//如果Optional实例有值则为其调用consumer，否则不做处理
		empty.ifPresent((value)->{
			System.out.println(value.length());
		});
		
		//orElse如果有值则将其返回，否则返回指定的其它值。
		//orElseGet    orElseGet与orElse方法类似，区别在于得到的默认值。
		//orElse方法将传入的字符串作为默认值，orElseGet方法可以接受Supplier接口的实现用来生成默认值。
		System.out.println(empty.orElse("NO Value"));
		System.out.println(empty.orElseGet(()->"No Value"));
		
		//orElseThrow 如果有值则将其返回，否则抛出supplier接口创建的异常。
		/*try {
			empty.orElseThrow(Exception::new);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//map 、 flatMap 、filter
		//如果有值，为其执行mapping函数返回Optional类型返回值，否则返回空Optional。flatMap与map（Funtion）方法类似，
		//区别在于flatMap中的mapper返回值必须是Optional。调用结束时，flatMap不会对结果用Optional封装。
		System.out.println(name.map(x->x.toUpperCase()));
		System.out.println(name.flatMap(x->Optional.of(x.toUpperCase())));
		System.out.println(name.filter(x->!x.equals("plf")));
		
		
		Person person=new Person();
		System.out.println("PersonName==》"+getNewName(person));
	
		person.setName("yoooo");
		System.out.println("PersonName==》"+getNewName(person));
	}
	
	
	public static String getNewName(Person p) {
	    return Optional.ofNullable(p)
	                    .map(x->p.getName())
	                    .orElse("Unknown");
	}
}