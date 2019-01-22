package com.plf.enums;

public class EnumTest {
	public static void main(String[] args) {
		System.out.println(WeekDay.MON);//MON
		System.out.println(WeekDay.MON.ordinal());//1
		System.out.println(WeekDay.valueOf("SUN").toString());//SUN
		System.out.println(WeekDay.values());//[Lcom.plf.enums.WeekDay;@4e25154f
		System.out.println(WeekDay.MON.getName());//星期一
	}
}
