package com.plf.zookeeper.base;


//import java.util.List;
import java.util.concurrent.CountDownLatch;

//import org.apache.zookeeper.AsyncCallback.VoidCallback;
//import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
//import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/*
ZooKeeper(Arguments)构造方法，通过参数的不同构造
connectString	连接服务器列表，使用","分割
sessionTimeout 	心跳检测时间周期 单位毫秒
watcher	事件处理通知器
canBeReadOnly	标识当前会话是否支持只读
sessionId sessionPassword 提供连接Zookeeper的sessionId和密码，通过这两个确定唯一一台客户端
					目的是可以提供重复会话
					
Zookeeper客户端和服务端会话的建立是一个异步的过程

创建节点
	节点路径	不允许递归创建节点，也就是说父节点不存在的情况下，不允许创建子节点
	节点内容	不支持序列化方式，如需实现序列化，可使用java相关序列化框架	Hessian kryo框架
	节点权限	Ids.OPEN_ACL_UNSAFE开放权限即可	这个参数在权限没有太高的要求场景下，没有必要关注
	节点类型	CreateMode.*
	PERSISTENT	持久节点	
	PERSISTENT_SEQUENTIAL	持久顺序节点
	EPHEMERAL	临时节点
	EPHEMERAL_SEQUENTIAL 临时顺序节点
 */
public class ZooKeeperBase {

	//zookeeper地址
	//"127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184"
	static final String CONNECT_ADDR = "127.0.0.1:2181";
	
	//超时时间
	static final int SESSION_OUTTIME = 5000;//ms
	
	//阻塞程序执行，用于等待Zookeeper连接成功，发送成功信号
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				//获取事件状态
				KeeperState keeperState = event.getState();
				//获取事件的类型
				EventType eventType = event.getType();
				//如果是建立连接
				if(KeeperState.SyncConnected == keeperState){
					//如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
					if(EventType.None == eventType){
						connectedSemaphore.countDown();
						System.out.println("Zookeeper建立连接");
					}
				}
			}
		});
		//进行阻塞
		connectedSemaphore.await();
		
		//System.out.println("执行程序");
		
		/**
		 * 创建节点
		 * path	创建节点路径
		 * data 数据
		 * acl 认证
		 * createMode  节点模式
		 */
		//zk.create("/testRoot","firstRoot".getBytes(),	Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
		
		//创建子节点
		//zk.create("/testRoot/children","firstRootChildren".getBytes(),	Ids.OPEN_ACL_UNSAFE , CreateMode.PERSISTENT);
		
		
		//获取节点信息
		/*byte[] data = zk.getData("/testRoot", false, null);
		System.out.println(new String(data));
		List<String> list = zk.getChildren("/testRoot", false);
		for (String path : list) {
			System.out.println(path);
			String realPath = "/testRoot/"+path;
			System.out.println(new String(zk.getData(realPath, false, null)));
		}*/
		
		//异步操作 -- 删除
		//-1 跳过版本检查
		//zk.delete("/testRoot/children", -1, new VoidCallback() {
			/**
			 * 
			 * @param rc 服务端响应码 0 表示调用成功	-4 表示端口连接 -110 表示指定节点存在 -112表示会话已经过期
			 * @param path	接口调用时传入API的数据节点的路径参数
			 * @param ctx	为调用接口传入API的ctx值
			 * @Param name 实际在服务器端创建节点的名称
			 */
			//public void processResult(int rc, String path, Object ctx) {
			//	System.out.println(rc);
			//	System.out.println(path);
			//	System.out.println(ctx);
			//}
		//}, "a");

		//修改节点
		//zk.setData("/testRoot/children", "udpate data".getBytes(), -1);
		
		//判断节点是否存在，不存在则是null
		//System.out.println(zk.exists("/testRoot/children", false));
		
		//删除，不支持递归删除
		//zk.delete("/testRoot/children", -1);
		
		zk.close();
	}
}
