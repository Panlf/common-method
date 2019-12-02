package com.plf.guava;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;

/**
 * Splitter分割字符串
 * @author plf 2019年12月2日上午11:12:02
 *
 */
public class GuavaSplitterDemo {
	private final static Logger log = LoggerFactory.getLogger(GuavaSplitterDemo.class);
	
	/**
	 * 根据'|'分割字符串
	 */
	@Test
	public void testSplitterOn(){
		List<String> list = Splitter.on("|").splitToList("hello|world");
		log.info(list.toString());
	} 
	
	/**
	 * 根据'|'分割字符串，去除空白
	 */
	@Test
	public void testSplitterOmitEmpty(){
		List<String> list = Splitter.on("|").trimResults().omitEmptyStrings().splitToList("hello|world||||");
		log.info(list.toString());
	} 
	
	/**
	 * 按照字长分割
	 */
	@Test
	public void testSplitterFixLength(){
		List<String> list = Splitter.fixedLength(4).splitToList("aaaabbbbccccdddd");
		log.info(list.toString());
	} 
	
	
	/**
	 * limit 分成3个 元素
	 * [hello, world, java,python,go]
	 */
	@Test
	public void testSplitterLimit(){
		 List<String> list = Splitter.on(",").limit(3).splitToList("hello,world,java,python,go");
		 log.info(list.toString());
	} 
	
	@Test
	public void testSplitterPattern(){
		 List<String> list = Splitter.onPattern("\\|").trimResults().omitEmptyStrings().splitToList("hello | world ||||");
		 log.info(list.toString());
	} 
	
	/**
	 * 根据正则表达式分割字符串
	 */
	@Test
	public void testSplitterPatternCompile(){
		 List<String> list = Splitter.on(Pattern.compile("[0-9]"))
				 .trimResults().omitEmptyStrings()
				 .splitToList("hello1world2java");
		 log.info(list.toString());
	} 
	
	/**
	 * 根据正则表达式分割字符串生成Map
	 */
	@Test
	public void testSplitterToMap(){
		 Map<String,String>	list = Splitter.on(Pattern.compile("\\|"))
				 .trimResults().omitEmptyStrings()
				 .withKeyValueSeparator("=")
				 .split("hello=HELLO|world=WORLD");
		 log.info(list.toString());
	} 
	
}
