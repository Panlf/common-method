package com.plf.common.reflect.benchmark;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author panlf
 * @date 2021/11/9
 */
public class AppTest {

    @Test
    public void streetCall(){
        App app = new App();
        long start = System.nanoTime();
        for(int i = 0;i<10000;i++){
            app.run(i);
        }
        //698400ns
        System.out.println((System.nanoTime()-start)+"ns");
    }

    @Test
    public void reflectCall() throws Exception {
        Class<?> clazz = App.class;
        Object obj = clazz.getDeclaredConstructor().newInstance();
        long start = System.nanoTime();
        for(int i = 0;i<10000;i++){
            Method method = clazz.getMethod("run",int.class);
            method.invoke(obj,i);
        }
        //24991600ns
        System.out.println((System.nanoTime()-start)+"ns");
    }

    @Test
    public void reflectCall1() throws Exception {
        Class<?> clazz = App.class;
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("run",int.class);
        long start = System.nanoTime();
        for(int i = 0;i<10000;i++){
            method.invoke(obj,i);
        }
        //6414300ns
        System.out.println((System.nanoTime()-start)+"ns");
    }

    @Test
    public void reflectCall2() throws Exception {
        Class<?> clazz = App.class;
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod("run",int.class);
        method.setAccessible(true);
        long start = System.nanoTime();
        for(int i = 0;i<10000;i++){
            method.invoke(obj,i);
        }
        //3938900ns
        System.out.println((System.nanoTime()-start)+"ns");
    }

}
