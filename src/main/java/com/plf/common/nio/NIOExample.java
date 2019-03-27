package com.plf.common.nio;

import java.nio.IntBuffer;

import org.junit.Test;

/*
 * NIO特性
 * 为所有的原始类型提供(Buffer)缓存支持
 * 字符集编码解码解决方案
 * Channel:一个新的原始I/O抽象
 * 支持锁和内存映射文件的文件访问接口
 * 提供多路(non-bloking)非阻塞式的高伸缩性网络I/O
 */
public class NIOExample {
	
	//所有缓冲区都具有四个属性来提供关于其所包含的数据元素的信息
	@SuppressWarnings("static-access")
	/*1、容量  缓冲区能够容纳的数据元素的最大数量
	 *2、上界 缓冲区的第一个不能被读或写的元素
	 *3、位置 下一个要被读或写的元素的索引
	 *4、标记 一个备忘位置
	 **/
	//缓冲区分类:ByteBuffer、CharBuffer、DoubleBuffer、FloatBuffer、IntBuffer、LongBuffer、ShortBuffer
	@Test
	public void TestBuffer(){
		//创建指定长度的缓冲区
		IntBuffer buff=IntBuffer.allocate(10);
		int[] array=new int[]{1,3,5};
		
		//使用数组来创建一个缓冲区视图
		buff = buff.wrap(array);
		
		//利用数组的某一个区间来创建视图
		//buff = buff.wrap(array,0,2);
		
		//对缓冲区某个位置上面的元素修改
		buff.put(0,8);
		
		//遍历缓冲区的数据
		for(int i=0;i<buff.limit();i++){
			System.out.print(buff.get()+"\t");
		}
		System.out.println("\nBuffer类信息："+buff);
		buff.flip();//对缓冲区进行反转，(limit=pos;pos=0)
		System.out.println("\nBuffer类信息："+buff);
		//buff.clear();
		
		//赋值一个新的缓冲区
		IntBuffer newBuff=buff.duplicate();
		System.out.println(newBuff);
	}
	
}
