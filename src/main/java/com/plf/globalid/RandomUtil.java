package com.plf.globalid;

import java.text.SimpleDateFormat;
import java.util.concurrent.ThreadLocalRandom;

import org.joda.time.DateTime;

/**
 * 不适合在高并发环境中使用，建议使用SnowFlake方式
 * @author plf 2020年3月2日下午4:07:23
 *
 */
public class RandomUtil {
	
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
	
	private static final ThreadLocalRandom random  = ThreadLocalRandom.current();
	
	/**
	 * 时间戳+N为流水号
	 * @return
	 */
	public static String generateOrderCode() {
		return simpleDateFormat.format(DateTime.now().toDate())+generateNumber(4);
	}


	public static String generateNumber(int num) {
		StringBuffer sb = new StringBuffer();
		for(int i=1;i<=num;i++) {
			sb.append(random.nextInt(9));
		}
		return sb.toString();
	}
}
