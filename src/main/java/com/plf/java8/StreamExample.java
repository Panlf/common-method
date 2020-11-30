package com.plf.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

/**
 * Stream
 * @author plf 2017年6月15日下午12:42:51
 *
 */
public class StreamExample {
	/**
	 * 一、
	 * 1、Stream 在Java8中被定义为泛型接口
	 * 2、Stream接口代表数据流
	 * 3、Stream不是数据结构，不直接存储数据
	 * 4、Stream通过管道操作数据
	 * 5、创建Stream接口实现类对象
	 * 		stream():创建一个Stream接口实现类的对象
	 * 二、
	 * 管道:代表一个操作序列
	 * 管道包含一下组件
	 * 	1、数据集:可能是集合、数组等
	 * 	2、零个或多个中间业务。如过滤器
	 * 	3、一个终端操作，如forEach
	 * 
	 * 三、
	 * 过滤器:用给定的条件在源数据基础上过滤出新的数据，并返回一个Stream对象
	 * 2、过滤器包含匹配的谓词
	 * 		例如：判断p对象是否为男性的lambda表达式
	 * Stream<Person> stream=people.stream();
	 * stream=stream.filter(p->p.getGender()=='男');
	 * 
	 * 四、
	 * DoubleStream 接口表示元素类型是double的数据源
	 * DoubleStream 接口的常用方法
	 * max().getAsDouble() 获取流中数据集的最大值
	 * stream().min().getAsDouble 获取流中数据集的最小值
	 * stream.average() 获取流中数据集的平均值 
	 */
	
	//创建一个元素为Person类的集合：people使用stream和forEach显示该集合所有元素
	@Test
	public void CollectionStream(){
		List<Person> people=createPeople();
		Stream<Person> stream=people.stream();
		
		//stream.forEach(p->System.out.println(p.toString()));
		
		//过滤器--过滤FEMALE
		//stream.filter(p->p.getGender()==Person.Sex.MALE).
		//forEach(p->System.out.println(p.toString()));
		
		double a=stream.filter(p->p.getName().indexOf("王")>=0)
		.mapToDouble(p->p.getHeight())
		.average()
		.getAsDouble();
		System.out.println(a);
	}
	
	/**
	 * forEach迭代流中的每一个元素
	 */
	@Test
	public void testForeach() {
		Random random = new Random();
		List<Integer> list= new ArrayList<>();
		random.ints(-100,100).limit(10).forEach(t->list.add(t));
		System.out.println(list);
	}
	
	@Test
	public void testFilter() {
		List<Integer> intList = Arrays.asList(8,2,4,1,8,3,10,6,6,15);
		List<Integer> newIntList=intList.stream().filter(i->i>5).sorted().distinct().collect(Collectors.toList());
		System.out.println(newIntList);
	}
	
	/**
	 * map对流中每个元素进行操作，返回一个新的流
	 */
	@Test
	public void testMap(){
		// 只是对流操作并不会改变原List中的数据
		//Stream<String> fruit=Stream.of("apple","orange","banner","pear");
		List<String> fruit = Arrays.asList("apple","orange","banner","pear");
		fruit.stream().sorted().map(String::toUpperCase).forEach(System.out::println);
		System.out.println(fruit);

		// 返回新的流
		List<String> newfruit = fruit.stream().map(v->v.toUpperCase()).collect(Collectors.toList());
		System.out.println(newfruit);
	}
	
	/**
	 * 测试并行流
	 */
	@Test
	public void testParallel() {
		Random random = new Random();
		List<Integer> list= new ArrayList<>();
		random.ints(-10000,10000).limit(10000).forEach(t->list.add(t));
		
		
		long start = System.currentTimeMillis();
		list.stream().filter(e -> e > 1000 && e< 2000).collect(Collectors.toList());
	    System.out.println("stream : " + (System.currentTimeMillis() - start) + "ms");
		
	    start = System.currentTimeMillis();
	    list.parallelStream().filter(e -> e > 1000 && e < 2000).collect(Collectors.toList());
	    System.out.println("parallelStream : " + (System.currentTimeMillis() - start) + "ms");
	}
	
	@Test
	public void testCount() {
		List<Integer> numList = Arrays.asList(3, 2, 2, 3, 7, 3, 5, 9);
		 
		DoubleSummaryStatistics stats = numList.stream().mapToDouble((x) -> x).summaryStatistics();
		System.out.println("总个数 : " + stats.getCount());
		System.out.println("列表中最大的数 : " + stats.getMax());
		System.out.println("列表中最小的数 : " + stats.getMin());
		System.out.println("所有数之和 : " + stats.getSum());
		System.out.println("平均数 : " + stats.getAverage());
	}
	
	/**
	 * 取出对象列表中某一个值并用某一字符连接
	 */
	@Test
	public void CollectionStreamJoin(){
		List<Person> people=createPeople();
		Stream<Person> stream=people.stream();
		String names = stream.map(v->v.getName()).collect(Collectors.joining(","));
		System.out.println(names);
	}
	
	static List<Person> createPeople(){
		List<Person> people=new ArrayList<Person>();
		Person person=new Person("张三",0,30,2.8);
		people.add(person);
		person=new Person("李四",0,32,1.6);
		people.add(person);
		person=new Person("王五",1,32,2.0);
		people.add(person);
		person=new Person("王五",1,33,1.6);
		people.add(person);
		return people;
	}
	
	/**
	 * List分组并根据另外一个字段大小选取
	 */
	@Test
	public void getOnlyOneByField() {
		List<Person> people=createPeople();
		Map<String,Person> map =new HashMap<>();
		map = people.parallelStream()
					.collect(Collectors.groupingBy(Person::getName,
							Collectors.collectingAndThen(
									Collectors.reducing((c1, c2) -> c1.getAge()>c2.getAge()?c1:c2),
									Optional::get)));
		System.out.println(JSON.toJSONString(map));
	}
	
	@Test
	public void sortField() {
		List<Person> people=createPeople();
		people.sort(Comparator.comparing(Person::getAge).reversed().thenComparing(Person::getHeight));
		System.out.println(JSON.toJSONString(people));
	}
	
	/**
	 * 根据某个字段取唯一值
	 */
	@Test
	public void distinctList() {
		List<Person> people=createPeople();
		List<Person> distinctlist = people.stream()
				.collect(Collectors.collectingAndThen(
						Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Person::getName))),
						ArrayList::new));
		System.out.println(JSON.toJSONString(distinctlist));
	}
	
	/**
	 * Long 数组选取不为Null和大于0的值
	 * @param t
	 * @return
	 */
	public static Long[] removeNullAndZero(Long[] t) {
		List<Long> list = Arrays.asList(t);
		return list.stream().filter(v->v!=null && v>0).toArray(Long[] :: new);	
	}
	
	/**
	 * 选取String数组 不为Null的值
	 * @param t
	 * @return
	 */
	public static String[] removeNullAndZero(String[] t) {
		List<String> list = Arrays.asList(t);
		return list.stream().filter(v->v!=null).toArray(String[] :: new);	
	}

}