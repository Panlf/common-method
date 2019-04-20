package com.plf.zookeeper.base;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZooKeeperWatcher implements Watcher {

	//定义原子变量
	AtomicInteger seq = new AtomicInteger(0);
	
	//定义session失效时间
	private static final int SESSION_TIMEOUT = 10000;
	
	//zookeeper服务器地址
	private static final String CONNECT_ADDR = "127.0.0.1:2181";
	
	private static final String PARENT_PATH="/p";
	
	private static final String CHILDREN_PATH="/p1";
	
	private static final String LOG_PREFIX_OF_MAIN = "【main】";
	 
	private ZooKeeper zk = null;
	
	ArrayList<ACL> acls = Ids.CREATOR_ALL_ACL;
	
	
	//阻塞程序执行，用于等待Zookeeper连接成功，发送成功信号
	private static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	@Override
	public void process(WatchedEvent event) {
		System.out.println("进入process....event="+event);
		
		if(event == null){
			return;
		}
		
		//获取事件状态
		KeeperState keeperState = event.getState();
		//获取事件的类型
		EventType eventType = event.getType();
		//受影响的path
		//String path = event.getPath();
		//原子对象seq记录进入process的次数
		String logPrefix = "【Watcher - "+this.seq.incrementAndGet()+"】";
		
		System.out.println(logPrefix+"收到Watcher通知");
		System.out.println(logPrefix+"连接状态:\t"+keeperState.toString());
		System.out.println(logPrefix+"事件类型:\t"+eventType.toString());
		
		//如果是建立连接
		if(KeeperState.SyncConnected == keeperState){
			//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
			if(EventType.None == eventType){
				System.out.println(logPrefix+"成功连接上ZK服务器");
				connectedSemaphore.countDown();
			}else if(EventType.NodeCreated==eventType){
				System.out.println(logPrefix+"节点创建");
			}else if(EventType.NodeDataChanged==eventType){
				System.out.println(logPrefix+"节点数据更新");
			}else if(EventType.NodeChildrenChanged==eventType){
				System.out.println(logPrefix+"子节点变更");
			}else if(EventType.NodeDeleted==eventType){
				System.out.println(logPrefix+"节点删除");
			}
		}else if(KeeperState.Disconnected==keeperState){
			System.out.println(logPrefix+"与ZK服务器断开连接");
		}else if(KeeperState.AuthFailed==keeperState){
			System.out.println(logPrefix+"权限检查失败");
		}else if(KeeperState.Expired==keeperState){
			System.out.println(logPrefix+"会话失败");
		}else;
		

	}
	
	public boolean createPath(String path,String data,boolean needWatch){
		try {
			this.zk.exists(path, needWatch);
			System.out.println(LOG_PREFIX_OF_MAIN+"节点创建成功,path:"+
					this.zk.create(
							path,
							data.getBytes(), 
							Ids.OPEN_ACL_UNSAFE,
							CreateMode.PERSISTENT)
							+",content:"+data);
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean writeData(String path, String data) {
		try {
			System.out.println(LOG_PREFIX_OF_MAIN+"更新数据成功,path:"+path+",stat:"
					+this.zk.setData(path, data.getBytes(), -1));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public String readData(String path, boolean needWatch) {
		try {
			System.out.println(LOG_PREFIX_OF_MAIN+"读取数据操作");
			return new String(this.zk.getData(path, needWatch, null));
		} catch (KeeperException | InterruptedException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public void deleteAllPath(boolean needWatch) throws KeeperException, InterruptedException{
		if(this.zk.exists(CHILDREN_PATH, needWatch)!=null){
			this.deleteNode(CHILDREN_PATH);
		}
		if(this.zk.exists(PARENT_PATH, needWatch)!=null){
			this.deleteNode(PARENT_PATH);
		}
	}
	
	public void deleteNode(String childrenPath) {
		try {
			this.zk.delete(childrenPath, -1);
		} catch (InterruptedException | KeeperException e) {
			e.printStackTrace();
		}
	}

	public void createConnection(String connectAddr,int sessionTimeout){
		this.releaseConnection();
		try {
			//this表示把当前对象进行传递到其中去
			zk = new ZooKeeper(CONNECT_ADDR, SESSION_TIMEOUT, this);
			System.out.println(LOG_PREFIX_OF_MAIN+"开始连接ZK服务器");
			connectedSemaphore.countDown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭Zookeeper
	 */
	private void releaseConnection() {
		if(this.zk!=null){
			try {
				this.zk.close();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		ZooKeeperWatcher zkWatcher = new ZooKeeperWatcher();
		
		zkWatcher.createConnection(CONNECT_ADDR, SESSION_TIMEOUT);
		
		Thread.sleep(1000);
		
		if(zkWatcher.createPath(PARENT_PATH, System.currentTimeMillis()+"", true)){
			zkWatcher.writeData(PARENT_PATH,System.currentTimeMillis()+"");
		}
	}

	
}
