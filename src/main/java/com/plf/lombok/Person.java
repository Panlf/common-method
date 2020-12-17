package com.plf.lombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
/**
 * 使用lombok消除冗余代码
 * @author plf 2017年10月18日下午10:32:38
 *
 */
@AllArgsConstructor
@Builder
@ToString
public @Data class Person {
	private String name;
	private int age;
	private String sex;
}
