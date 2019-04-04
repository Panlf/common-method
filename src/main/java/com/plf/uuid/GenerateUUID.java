package com.plf.uuid;

import java.util.UUID;

import org.junit.Test;

import com.fasterxml.uuid.Generators;

/**
 * Java 工具类获取UUID
 * @author plf 2019年4月4日下午3:19:51
 *
 */
public class GenerateUUID {

	
	@Test
	public void generateRandomUUID(){
		UUID uuid = null;
		for (int i = 0; i < 5; i++) {
			uuid = Generators.randomBasedGenerator().generate();
			System.out.println(uuid);
		}
		/*
		 	f8070362-2ae1-414f-8703-622ae1814fd7
			9819dd80-d054-4aaa-99dd-80d0544aaa9e
			bead7a21-1486-48bf-ad7a-21148628bf4f
			8d58450b-5e1d-4141-9845-0b5e1d814188
			14d0bb17-d3b6-4511-90bb-17d3b66511d9 
		 */
	}
	
	@Test
	public void generateTimeUUID(){
		UUID uuid = null;
		for (int i = 0; i < 5; i++) {
			uuid = Generators.timeBasedGenerator().generate();
			System.out.println(uuid);
		}
		/*
		82a74503-56aa-11e9-8437-4b8fa601b10a
		82a79324-56aa-11e9-8437-57fbcf8bdedd
		82a79325-56aa-11e9-8437-b715567eecf7
		82a79326-56aa-11e9-8437-37a9d2b3ba62
		82a79327-56aa-11e9-8437-b56bc3fb812b
		*/
	}
	
}
