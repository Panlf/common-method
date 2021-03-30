package com.plf.common.reflect;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JSON {

	/**
	 * 生成JSON的字符串
	 * 思路
	 * 1、得到object的Class对象
	 * 2、得到class里面的所有属性
	 * 3、循环遍历所有属性并打破属性的访问权限
	 * 4、得到属性名作为json的key
	 * 5、得到属性值作为json的value
	 * 6、把值和value进行拼装"id":1
	 * 7、再次循环重复3-6
	 * 8、返回拼接好的字符串
	 * @param object
	 * @return
	 */
	
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	public static String toJSONString(Object object) {
		StringBuffer json = new StringBuffer("{");
		
		try {
			Class<?> clazz = object.getClass();
			
			Field[] fields = clazz.getDeclaredFields();
			
			int index = 0;
			int length = fields.length;
			
			for(Field field:fields) {
				index++;
				
				//判断属性上是否有忽略的注解
				if(field.isAnnotationPresent(JsonIgnore.class)) {
					continue;
				}
				
				field.setAccessible(true);
				String name = field.getName();
				if(field.isAnnotationPresent(JsonProperty.class)) {
					//取出注解上的值
					JsonProperty jsonProperty = field.getDeclaredAnnotation(JsonProperty.class);
					name = jsonProperty.value();
				}
				
				Object value = field.get(object);
			
				json.append("\""+name+"\"");
				if(value instanceof String) {
					json.append(":\""+value.toString()+"\"");
				}else if(value instanceof Date){
					if(field.isAnnotationPresent(JsonFormat.class)) {
						JsonFormat jsonFormat = field.getDeclaredAnnotation(JsonFormat.class);
						dateFormat = new SimpleDateFormat(jsonFormat.pattern());
						json.append(":"+dateFormat.format((Date)value));
					}else {
						json.append(":"+dateFormat.format((Date)value));
					}
				}else {
					json.append(":"+value.toString());
				}
				
				if(index!=length) {
					json.append(",");
				}
			}
			
			
			
			json.append("}");
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public static <T> T parseObject(String json,Class<T> clazz) {
		try {
			T t= clazz.newInstance();
			String newStr = json.replace("{", "").replace("}", "");
			String[] split = newStr.split(",");
			for(String s:split) {
				String newS = s.replace("\"", "");
				String[] keyValue = newS.split(":");
				
				
				String key = keyValue[0];
				// 骚操作 针对时间日期的操作
				String value = newS.replaceFirst(key+":", "");
				//String value = keyValue[1];
				
				String name = key;
				Field field = null;
				try {
					field = clazz.getDeclaredField(key);
				}catch(Exception e){
					name = getProperty(clazz,key);
				}
				
				if(name.length()>0) {
					field = clazz.getDeclaredField(name);
				}else {
					continue;
				}
				
				field.setAccessible(true);
				String paramType = field.getType().getSimpleName();
				if(paramType.equals("Date")) {
					Date date = dateFormat.parse(value);
					field.set(t, date);
				}else if(paramType.equals("Integer")) {
					field.set(t,Integer.valueOf(value));
				}else if(paramType.equals("Double")) {
					field.set(t,Double.valueOf(value));
				}else {
					field.set(t, value);
				}
			}
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getProperty(Class<?> clazz,String propertyName) {
		
		String name = "";
		Field[] fields = clazz.getDeclaredFields();
		
		for(Field field:fields) {
			if(field.isAnnotationPresent(JsonProperty.class)) {
				JsonProperty jsonProperty = field.getDeclaredAnnotation(JsonProperty.class);
				if(propertyName.equals(jsonProperty.value())) {
					return field.getName();
				}
			}	
		}
		System.out.println(name);
		return name;
	}
	
	public static void main(String[] args) {
		/*Employee employee = new Employee(1,"Panlf",5000d,new Date());
		String result=toJSONString(employee);
		System.out.println(result);*/
		String result="{\"username\":\"Panlf\",\"salary\":5000.0,\"workDate\":2021-03-25 11:12:00}";
		Employee newEmployee = parseObject(result,Employee.class);
		System.out.println(newEmployee);
	}
}
