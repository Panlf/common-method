package com.plf.pattern.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestJdkProxy {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DoService target = new DoServiceImpl();
		// 使用JDK的Proxy动态代理，要求目标类必须实现接口
		// 因为其底层的执行原理，与静态代理相同
		DoService service = (DoService) Proxy.newProxyInstance(target.getClass().getClassLoader(), // 目标类的类加载器
				target.getClass().getInterfaces(), // 目标类所实现的所有接口
				new InvocationHandler() { // 匿名内部类

					// proxy：代理对象
					// method：目标方法
					// args 目标方法的参数列表
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						System.out.println("代理类--业务处理前操作");
						// 调用目标方法
						Object result = method.invoke(target, args);
						System.out.println("代理类--业务处理后操作");
						return result;
					}
				});

		service.doSomething();
	}
}