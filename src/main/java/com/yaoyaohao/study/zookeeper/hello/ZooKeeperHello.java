package com.yaoyaohao.study.zookeeper.hello;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper "hello world"演示
 * 
 * @author liujianzhu
 * @date 2016年9月26日 下午2:32:20
 */
public class ZooKeeperHello {
	private final static int SESSION_TIMEOUT = 60000;

	private final static CountDownLatch countDownLatch = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper("172.16.10.1:2181", SESSION_TIMEOUT, new DemoWatcher());
		countDownLatch.await();
		//
		String node = "/app1";
		Stat stat = zk.exists(node, false);
		if (stat == null) {
			// 创建节点
			String createResult = zk.create(node, "helloWorld".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			System.out.println("创建节点返回结果： " + createResult);
		}
		// 获取节点信息
		byte[] b = zk.getData(node, false, stat);
		System.out.println(new String(b));
		zk.close();
	}

	static class DemoWatcher implements Watcher {

		@Override
		public void process(WatchedEvent event) {
			if (event.getState() == KeeperState.SyncConnected) {
				countDownLatch.countDown();
			}
			System.out.println("--------------->");
			System.out.println("path : " + event.getPath());
			System.out.println("type : " + event.getType());
			System.out.println("stat : " + event.getState());
			System.out.println("<---------------");
		}

	}
}
