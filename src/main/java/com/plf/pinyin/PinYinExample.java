package com.plf.pinyin;

import org.junit.Test;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

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
	
	@Test
	public void TestToString(){
		HanyuPinyinOutputFormat outFormat = new HanyuPinyinOutputFormat();  
        outFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//控制拼音的大小写
        //是否带声调WITH_TONE_NUMBER：带声调 PAN1 LIANG2 FENG1
        //WITH_TONE_MARK 用声调符号表示
        outFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //特殊拼音u的显示格式
        outFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			System.out.println(PinyinHelper.toHanYuPinyinString("潘良烽", outFormat, " ",true));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestHanyuString(){
		//带声调
		String[] data=PinyinHelper.toHanyuPinyinStringArray('潘');
		for (String string : data) {
			System.out.println(string);
		}
		
		HanyuPinyinOutputFormat outFormat = new HanyuPinyinOutputFormat();  
        outFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);//控制拼音的大小写
        //是否带声调WITH_TONE_NUMBER：带声调 PAN1 LIANG2 FENG1
        //WITH_TONE_MARK 用声调符号表示
        outFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //特殊拼音u的显示格式
        outFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		try {
			//感觉比较奇怪的语法
			System.out.println(PinyinHelper.toHanyuPinyinStringArray('潘', outFormat)[0]);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
