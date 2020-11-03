package com.plf.common.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 读取src/main/resource下的文件
 * @author Panlf
 *
 */
public class ReadFileUtils {
	public static List<String> readTxtToList(String fileName) {
		List<String> list = new ArrayList<>();
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(
					new InputStreamReader(ReadFileUtils.class.getResourceAsStream("/" + fileName)));
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				list.add(lineTxt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	@Test
	public void test() {
		System.out.println(readTxtToList("test.properties"));
	}
}
