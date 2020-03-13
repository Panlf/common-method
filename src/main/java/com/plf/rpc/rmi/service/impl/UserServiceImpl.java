package com.plf.rpc.rmi.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.plf.rpc.rmi.service.UserService;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {
	
	private static final long serialVersionUID = -7593781495142216618L;

	public UserServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public String sayHello(String name) throws RemoteException {
		System.out.println("获取到姓名为: " +name);
		return name;
	}

}
