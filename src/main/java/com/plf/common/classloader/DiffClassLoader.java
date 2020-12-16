package com.plf.common.classloader;

import java.util.Arrays;
import java.util.List;

public class DiffClassLoader {
 
	public static void main(String[] args) {
		
		//bootClassLoaderLoadingPath();
		
		//extClassLoaderLoadingPath();
		
		//appClassLoaderLoadingPath();
		
		
		System.out.println(DiffClassLoader.class.getClassLoader());
		System.out.println(DiffClassLoader.class.getClassLoader().getParent());
		System.out.println(DiffClassLoader.class.getClassLoader().getParent().getParent());
		
		// 源码 Launcher类
	}
	
	/**
	 * 启动类加载器加载的职责
	 */
	public static void bootClassLoaderLoadingPath() {
		String bootStrapLoadingPath = System.getProperty("sun.boot.class.path");
		List<String> bootLoadingPathList = Arrays.asList(bootStrapLoadingPath.split(";"));
		
		for(String s:bootLoadingPathList){
			System.out.println("【启动类加载器  -- 加载的目录】"+s);
		}
	}
	
	/**
	 * 扩展类加载器加载的职责
	 */
	public static void extClassLoaderLoadingPath() {
		String extStrapLoadingPath = System.getProperty("java.ext.dirs");
		List<String> bootLoadingPathList = Arrays.asList(extStrapLoadingPath.split(";"));
		
		for(String s:bootLoadingPathList){
			System.out.println("【扩展类加载器  -- 加载的目录】"+s);
		}
	}
	
	/**
	 * 应用类加载器加载的职责
	 */
	public static void appClassLoaderLoadingPath() {
		String appStrapLoadingPath = System.getProperty("java.class.path");
		List<String> bootLoadingPathList = Arrays.asList(appStrapLoadingPath.split(";"));
		
		for(String s:bootLoadingPathList){
			System.out.println("【系统类加载器  -- 加载的目录】"+s);
		}
	}
}
