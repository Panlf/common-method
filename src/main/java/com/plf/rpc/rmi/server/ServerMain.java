package com.plf.rpc.rmi.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import com.plf.rpc.rmi.service.UserService;
import com.plf.rpc.rmi.service.impl.UserServiceImpl;

public class ServerMain {

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException {
		//1、启动RMI注册服务，指定端口号
		LocateRegistry.createRegistry(8888);
		//2、创建要被访问的远程对象的实例
		UserService userService = new UserServiceImpl();
		//3、把远程对象注册到RMI服务器上
		Naming.bind("rmi://localhost:8888/userService", userService);
	}

}
