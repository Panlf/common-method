package com.plf.common.reflect;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author panlf
 * @date 2023/2/20
 */
public class ReflectGetConstants {
    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> clazz =  Class.forName("com.plf.common.reflect.FieldConstants");
        Field[] fields = clazz.getDeclaredFields();
        Arrays.stream(fields).forEach(field -> {
            try {
                System.out.println(field.getName()+" "+field.get(clazz));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
