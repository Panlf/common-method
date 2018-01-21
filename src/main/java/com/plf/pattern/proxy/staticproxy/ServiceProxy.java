package com.plf.pattern.proxy.staticproxy;

//代理类
public class ServiceProxy implements DoService {
	
	private DoService target;
	
	public ServiceProxy(){
		super();
		//创建目标对象
		target = new DoServiceImpl();
	}
	
	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("代理类--业务处理前操作");
		target.doSomething();
		System.out.println("代理类--业务处理后操作");
	}

}
