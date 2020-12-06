package com.plf.netty.rpc.provider;

import com.plf.netty.rpc.pubinterface.HelloService;

public class HelloServiceImpl implements HelloService{

	@Override
	public String hello(String message) {
		System.out.println("收到客户端消息="+message);
		if( message != null) {
			return "你好客户端，我已经收到你的消息 ["+message+"]";
		}else {
			return "你好客户端，我已经收到你的消息";
		}
	}
}
