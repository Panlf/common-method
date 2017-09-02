package com.plf.java8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

/**
 * LocalDate()
 * @author plf 2017年6月15日下午3:17:01
 *
 */
public class LocalDateExample {
	/**
	 * LocalDate.now()获取系统当前日期
	 * LocalDate.of(int year,int month,int dayOfMonth)
	 * 按指定日期创建LocalDate对象
	 * getYear() 返回日期中的年份
	 * getMonthValue() 返回日期中的月份
	 * getDayOfMonth() 返回日期中的日
	 */
	
	/**
	 * LocalTime --时分秒
	 * 
	 * LocalDateTime --年月日时分秒
	 */
	
	/**
	 * DateTimeFormatter 
	 * static ofPattern(String pattern)
	 * 
	 * LocalDateTime.parse(strDate,formatter)
	 */
	
	/**
	 * ZonedDateTime 处理日期和时间与相应的时区
	 * ZonedDateTime.now()
	 * 
	 * String format(DateTimeFormatter formatter)
	 */
	@Test
	public void TestLocalDate(){
		LocalDate localDate=LocalDate.now();
		System.out.println(localDate.getYear()+"年"+localDate.getMonthValue()+"月"+localDate.getDayOfMonth()+"日");
		System.out.println(localDate.toString());
	}
	
	@Test
	public void TestLocalTime(){
		LocalTime time=LocalTime.now();
		System.out.println(time.getHour()+"时"+time.getMinute()+"分"+time.getSecond()+"秒");
		System.out.println(time.toString());
	}
	
	@Test
	public void TestLocalDateTime(){
		LocalDateTime time=LocalDateTime.now();
		System.out.println(time.getYear()+"年"+time.getMonthValue()+"月"+time.getDayOfMonth()+"日"+time.getHour()+"时"+time.getMinute()+"分"+time.getSecond()+"秒");
		System.out.println(time.toString());
	}
	
	@Test
	public void TestPattern(){
		DateTimeFormatter formater=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime time=LocalDateTime.now();
		System.out.println(time.format(formater));
	}

	@Test
	public void TestZonedDateTime(){
		ZonedDateTime time=ZonedDateTime.now();
		DateTimeFormatter formater=DateTimeFormatter.ofPattern("yyyy/MM/dd HH/mm/ss");
		System.out.println(time.format(formater));
	}
	
	@Test
	//增加时间
	public void addTime(){
		LocalDateTime time=LocalDateTime.now();
		System.out.println(time.plusHours(-3));
	
		DateTimeFormatter formater=DateTimeFormatter.ofPattern("yyyy/MM/dd HH/mm/ss");
		System.out.println(formater.format(time));
		//增加3s
		System.out.println(formater.format(time.plusSeconds(3)));
	}
}