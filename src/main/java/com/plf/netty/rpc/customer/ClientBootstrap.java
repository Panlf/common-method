package com.plf.netty.rpc.customer;

import com.plf.netty.rpc.provider.client.NettyClient;
import com.plf.netty.rpc.pubinterface.HelloService;

public class ClientBootstrap {

	// 这里定义协议头
	public static final String providerName = "HelloService#hello#";
	
	public static void main(String[] args) {
		//创建一个消费者
		NettyClient customer = new NettyClient();
		
		HelloService service  = (HelloService) customer.
				getBean(HelloService.class, providerName);
		
		//通过代理对象调用服务提供者的方法
		String result = service.hello("你好 RPC~");
		System.out.println("调用结果 result = "+result);
	}
}

