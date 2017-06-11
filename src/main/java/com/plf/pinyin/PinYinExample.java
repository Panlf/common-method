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
		//获取一个汉字的拼音,返回多音字，不带音标
		String[] array=PinyinHelper.toGwoyeuRomatzyhStringArray('都');//转为国语罗马字。
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
			//多个汉字
			System.out.println(PinyinHelper.toHanYuPinyinString("潘良烽", outFormat, "*",true));
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
	}
	
	@Test
	public void TestHanyuString(){
		//带声调
		//转为汉字拼音。中国大陆使用
		String[] data=PinyinHelper.toHanyuPinyinStringArray('都');
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
	
	@Test
	@SuppressWarnings("unused")
	public void TestPinyinType(){
		//返回多音字，带音标
		String[] data=PinyinHelper.toMPS2PinyinStringArray('地');//转为注音符号拼音。
		String[] data1=PinyinHelper.toTongyongPinyinStringArray('都');// 转为通用拼音。中国台湾使用
		String[] data2=PinyinHelper.toWadeGilesPinyinStringArray('都');//转为威妥玛拼音。
		String[] data3=PinyinHelper.toYalePinyinStringArray('都');//转为耶魯拼音。
		for (String string : data3) {
			System.out.println(string);
		}
	}
}
