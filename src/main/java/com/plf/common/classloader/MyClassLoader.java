package com.plf.common.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{

	/**
	 * 加载类的路径
	 */
	private String path;
	
	public MyClassLoader(String path) {
		//让系统类加载器称为该类的父加载器
		super();
		this.path = path;
	}
	
	/**
	 * 调用父类加载器
	 * @param parent
	 * @param path
	 * @param name
	 */
	public MyClassLoader(ClassLoader parent,String path) {
		//显式指定父类加载器
		super(parent);
		this.path = path;
	}
	
	/**
	 * 加载我们自定义的类
	 * 
	 * 
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = readClassFileTOByteArray(name);
		return this.defineClass(name, data, 0 , data.length);
	}

	/**
	 * 获取.class文件的字节数组
	 * @param name2
	 * @return
	 */
	private byte[] readClassFileTOByteArray(String name) {
		InputStream is  =null;
		byte[] returnData = null;
		
		name = name.replaceAll("\\.", "/");
		
		String filePath = this.path + name + ".class";
		
		File file = new File(filePath);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			is = new FileInputStream(file);
			int temp = 0;
			while((temp = is.read())!=-1) {
				os.write(temp);
			}
			returnData = os.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(is!=null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return returnData;
	}
	
	
}
