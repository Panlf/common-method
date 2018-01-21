package com.plf.pattern.proxy.staticproxy;

public class TestStaticProxy {
	public static void main(String[] args) {
		DoService service = new ServiceProxy();
		service.doSomething();
	}
}
