package com.plf.guava;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * Strings处理字符串
 * @author plf 2019年12月2日上午11:24:06
 *
 */
public class GuavaStringsDemo {
	private final static Logger log = LoggerFactory.getLogger(GuavaStringsDemo.class);
	
	@Test
	public void testStringsMethod(){
		
		log.info(Strings.emptyToNull(""));
		
		log.info(Strings.nullToEmpty(null));
		
		log.info(Strings.nullToEmpty("hello"));
		
		log.info(Strings.commonPrefix("hello", "hi"));
		
		log.info(Strings.commonSuffix("hello", "do"));
		
		log.info(Strings.repeat("Al", 3));
		
		log.info(""+Strings.isNullOrEmpty(null));
		
		log.info(""+Strings.isNullOrEmpty(""));
		
		log.info(Strings.padStart("WALe", 5, 'H'));
		
		log.info(Strings.padEnd("ALEq", 5, 'H'));
	}
}
