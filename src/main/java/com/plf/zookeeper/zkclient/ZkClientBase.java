package com.plf.zookeeper.zkclient;


import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;

//import java.util.List;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

public class ZkClientBase {
	static final String CONNECT_ADDR = "127.0.0.1:2181";
	
	//超时时间
	static final int SESSION_OUTTIME = 5000;//ms
	
	public static void main(String[] args) {
		ZkClient zkclient = new ZkClient(new ZkConnection(CONNECT_ADDR),SESSION_OUTTIME);
		
		//对父节点添加子节点变化
		zkclient.subscribeChildChanges("/super", new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				System.out.println("parentPath:"+parentPath);
				
			}
		});
		
		//zkclient.subscribeDataChanges(path, listener);
		
		//zkclient.create(path, data, mode);
		//zkclient.createEphemeral("/super");
		//可以进行递归创建
		//zkclient.createPersistent("/p/p1",true);
		
		//zkclient.delete("/p");
		//递归删除
		//zkclient.deleteRecursive("/p");
		
		/*zkclient.createPersistent("/super","0000asdas");
		zkclient.createPersistent("/super/c1","111111");
		zkclient.createPersistent("/super/c2","asdas");
		List<String> list = zkclient.getChildren("/super");
		for (String l : list) {
			String real = "/super/"+l;
			System.out.println("path:"+real+",data:"+zkclient.readData(real));
		}*/
		
		
		String obj=zkclient.readData("/super/c1");
		System.out.println(obj);
		zkclient.writeData("/super/c1", "hhh");
		obj=zkclient.readData("/super/c1");
		System.out.println(obj);
		zkclient.close();
	}
}

