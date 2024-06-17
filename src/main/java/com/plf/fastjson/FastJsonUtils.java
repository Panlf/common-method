package com.plf.fastjson;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.KeyValue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONLibDataFormatSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author Breeze
 * @date 2024/1/1
 */
public class FastJsonUtils {
	
	private static final SerializeConfig CONFIG;

	static {
		CONFIG =  new SerializeConfig();
		CONFIG.put(java.util.Date.class, new JSONLibDataFormatSerializer());
		CONFIG.put(java.sql.Date.class, new JSONLibDataFormatSerializer());
	}

	private static final SerializerFeature[] FEATURES = {
            SerializerFeature.WriteMapNullValue,
			// list字段如果为null，输出为[]，而不是null
			SerializerFeature.WriteNullListAsEmpty,
			// 数值字段如果为null，输出为0，而不是null
			SerializerFeature.WriteNullNumberAsZero,
			// Boolean字段如果为null，输出为false，而不是null
			SerializerFeature.WriteNullBooleanAsFalse,
			// 字符类型字段如果为null，输出为""，而不是null
			SerializerFeature.WriteNullStringAsEmpty
	};

	public static String toJsonString(Object object) {
		return JSON.toJSONString(object, CONFIG, FEATURES);
	}

	public static String toJsonNoFeatures(Object object) {
		return JSON.toJSONString(object, CONFIG);
	}

	public static Object toBean(String text) {
		return JSON.parse(text);
	}

	public static <T> T toBean(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	/**
	 * 转换为数组
	 * @param text
	 * @return
	 * @param <T>
	 */
	public static <T> Object[] toArray(String text) {
		return toArray(text, null);
	}

	/**
	 * 转换为数组
	 * @param text
	 * @param clazz
	 * @return
	 * @param <T>
	 */
	public static <T> Object[] toArray(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz).toArray();
	}

	/**
	 * 转换为List
	 * @param text
	 * @param clazz
	 * @return
	 * @param <T>
	 */
	public static <T> List<T> toList(String text, Class<T> clazz) {
		return JSON.parseArray(text, clazz);
	}

	/**
	 * 将javabean转化为序列化的json字符串
	 * 
	 * @param keyvalue
	 * @return
	 */
	public static Object beanToJson(KeyValue<?, ?> keyvalue) {
		String textJson = JSON.toJSONString(keyvalue);
        return JSON.parse(textJson);
	}

	/**
	 * 将string转化为序列化的json字符串
	 * 
	 * @param
	 * @return
	 */
	public static Object textToJson(String text) {
        return JSON.parse(text);
	}

	/**
	 * 将map转化为string
	 * 
	 * @param m
	 * @return
	 */
	public static String collectToString(Map<?, ?> m) {
        return JSONObject.toJSONString(m);
	}

}
