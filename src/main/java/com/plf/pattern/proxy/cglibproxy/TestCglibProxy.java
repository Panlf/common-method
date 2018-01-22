package com.plf.pattern.proxy.cglibproxy;

public class TestCglibProxy {
	public static void main(String[] args) {
		DoService service = new MyCglibFactory().MyCglibCreator();
		service.doSomething();
	}
}
