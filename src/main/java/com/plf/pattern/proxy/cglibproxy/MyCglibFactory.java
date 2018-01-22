package com.plf.pattern.proxy.cglibproxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MyCglibFactory implements MethodInterceptor {

	private DoService target;
	
	public MyCglibFactory() {
		super();
		// TODO Auto-generated constructor stub
		target = new DoService();
	}
	
	public DoService MyCglibCreator(){
		//创建增强器对象
		Enhancer enhancer = new Enhancer(); 
		//指定目标类，即父类
		enhancer.setSuperclass(DoService.class);
		//设置回调接口对象
		enhancer.setCallback(this);
		
		return (DoService) enhancer.create();
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("代理类--业务处理前操作");
		Object result = method.invoke(target, args);
		System.out.println("代理类--业务处理后操作");
		return result;
	}

}
