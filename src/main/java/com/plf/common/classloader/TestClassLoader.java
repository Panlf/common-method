package com.plf.common.classloader;

public class TestClassLoader {

	public static void main(String[] args) throws Exception{
		//用父类的ClassLoader
		//MyClassLoader myClassLoader = new MyClassLoader("F:\\Technology\\Eclipse\\workspace\\CommonMethod\\target\\classes\\");
		
		//用自己的ClassLoader
		MyClassLoader myClassLoader = new MyClassLoader(null,"F:\\Technology\\Eclipse\\workspace\\CommonMethod\\target\\classes\\");
		Class<?> cls = myClassLoader.loadClass("com.plf.common.classloader.Demo");
		cls.newInstance();
	}

}
