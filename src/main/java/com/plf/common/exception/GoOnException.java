package com.plf.common.exception;

/**
 * 循环操作，遇到异常继续执行循环
 * @author plf 2020年3月18日下午4:23:14
 *
 */
public class GoOnException {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			try {
				if (i == 5) {
					throw new RuntimeException("不能等于5");
				}
				System.out.println(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
