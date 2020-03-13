package com.plf.rpc.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.plf.rpc.rmi.service.UserService;

public class ClientMain {

	public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
		UserService service = (UserService) Naming.lookup("rmi://localhost:8888/userService");
	
		String result = service.sayHello("JavaRMI");
	
		System.out.println(result);
	}

}
