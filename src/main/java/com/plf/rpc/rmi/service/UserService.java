package com.plf.rpc.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

	String sayHello(String name) throws RemoteException;
}
