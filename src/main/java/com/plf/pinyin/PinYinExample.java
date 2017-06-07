package com.plf.pinyin;

import org.junit.Test;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 拼音Jar的基本用法
 * @author plf 2017年6月7日下午10:23:01
 *
 */
public class PinYinExample {
	
	@Test
	public void TestPinyin(){
		//获取一个汉字的拼音
		String[] array=PinyinHelper.toGwoyeuRomatzyhStringArray('潘');
		for (String string : array) {
			System.out.println(string);
		}
	}
}
