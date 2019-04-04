package com.plf.common.io;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class FindFile {
	
	@Test
	public void test() {
		/*List<File> list=fiadAllFile_2(new File("E://logs"));
		for (File file : list) {
			if(file.toString().endsWith(".DS_Store")){
				System.out.println(file);
				file.delete();
			}
		}*/
		fiadAllFile(new File("E://logs"));
	}

	/**
	 * 递归查找文件
	 * @param dir
	 */
	public static void fiadAllFile(File dir){
		if(!dir.exists()){
			throw new IllegalArgumentException("目录："+dir+"不存在");
		}
		if(!dir.isDirectory()){
			throw new IllegalArgumentException(dir+"不是一个目录");
		}
		
		//获取当前第一层目录
		File[] files = dir.listFiles();
		
		
		if(files!=null && files.length>0){
			for (File file : files) {
				if(file.isDirectory()){
					fiadAllFile(file);
				}else{
					System.out.println(file);
				}
			}
		}
	}
	
	public static List<File> fiadAllFile_2(File dir){
		if(!dir.exists()){
			throw new IllegalArgumentException("目录："+dir+"不存在");
		}
		if(!dir.isDirectory()){
			throw new IllegalArgumentException(dir+"不是一个目录");
		}
		
		//获取当前第一层目录
		File[] files = dir.listFiles();
		
		System.out.println(files.length);
		//所有文件
		List<File> fileList = new LinkedList<File>();
		
		//目录
		List<File> dirs = new LinkedList<File>();
		
		if(files != null && files.length>0){
			for (File file : files) {
				if(file.isDirectory()){
					dirs.add(file);
				}else{
					fileList.add(file);
				}
			}
		}
		
		File fileTmp;
		
		while(!dirs.isEmpty()){
			fileTmp = dirs.remove(0);
			files = fileTmp.listFiles();
			if(files != null && files.length>0){
				for (File file : files) {
					if(file.isDirectory()){
						dirs.add(file);
					}else{
						fileList.add(file);
					}
				}
			}
		}
		
		return fileList;
	}
}
