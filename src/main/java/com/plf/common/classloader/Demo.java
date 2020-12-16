package com.plf.common.classloader;

public class Demo {
	public Demo() {
		System.out.println("Hello ClassLoader -- "+this.getClass().getClassLoader());
	}
}
