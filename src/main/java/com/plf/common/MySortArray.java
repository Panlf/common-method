package com.plf.common;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 关于Java自带的数组排序
 * @author plf 2017年10月18日下午10:05:03
 *
 */
public class MySortArray {

	public static void main(String[] args) {
		Integer[] arr=new Integer[]{3,7,5,9,1};
		System.out.print("排序前===》");
		inputArray(arr);
		//Arrays.sort(arr);//该方法只支持升序
		//Arrays.sort(arr,new MySort());//降序
		Arrays.sort(arr,new Comparator<Integer>(){
			//使用匿名函数
			public int compare(Integer o1, Integer o2) {
				// TODO Auto-generated method stub
				return o2.compareTo(o1);
			}
			
		});
		System.out.print("排序后===》");
		inputArray(arr);
	}
	
	
	private static void inputArray(Integer[] arr){
		if(arr!=null){
			for(int i=0;i<arr.length;i++){
				System.out.print(arr[i]+" ");
			}
			System.out.println();
		}
	 }
}

//自定义一个比较类
class MySort implements Comparator<Integer>{ 
	public int compare(Integer o1, Integer o2) { 
	if(o1 < o2){ 
		return 1; 
	}else if(o1 == o2){ 
		return 0; 
	}else{ 
		return -1; 
		} 
	} 
}

