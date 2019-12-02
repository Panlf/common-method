package com.plf.guava;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

/**
 * 
 * Joiner连接String
 * @author plf 2019年12月2日上午10:49:43
 *
 */
public class GuavaJoinerDemo {
	private final static Logger log = LoggerFactory.getLogger(GuavaJoinerDemo.class);
	
	private final List<String> stringList = Arrays.asList(
			"Google","Guava","Java","Scala","Solr","Kafka");
	
	private final List<String> stringListWithNullValue = Arrays.asList(
			"Google","Guava","Java","Scala","Solr",null);
	
	/**
	 * 用逗号连接每个元素
	 * Google,Guava,Java,Scala,Solr,Kafka
	 */
	@Test
	public void testJoinOnJoin(){
		String result = Joiner.on(",").join(stringList);
		log.info(result);
	}
	
	/**
	 * 当遇到NULL值该方法会报错
	 */
	@Test(expected=NullPointerException.class)
	public void testJoinOnJoinWithNull(){
		String result = Joiner.on(",").join(stringListWithNullValue);
		log.info(result);
	}
	
	/**
	 * 去除NULL值后连接元素
	 * Google,Guava,Java,Scala,Solr
	 */
	@Test
	public void testJoinOnJoinSkipWithNull(){
		String result = Joiner.on(",").skipNulls().join(stringListWithNullValue);
		log.info(result);
		
	}
	
	/**
	 * NULL用默认值替代
	 * Google,Guava,Java,Scala,Solr,DEFAUL
	 */
	@Test
	public void testJoinOnJoinSkipWithNullUseDefaultValue(){
		String result = Joiner.on(",").useForNull("DEFAULT").join(stringListWithNullValue);
		log.info(result);
	}
}
