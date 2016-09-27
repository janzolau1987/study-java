package com.yaoyaohao.study.zookeeper.createAndDelete;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 封闭zk基本操作，包括创建、关闭
 * 
 * @author liujianzhu
 * @date 2016年9月27日 下午5:03:22
 */
public class ConnectionWatcher implements Watcher {
	private static final int SESSION_TIMEOUT = 5000;

	protected ZooKeeper zk = null;
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	@Override
	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		if (state == KeeperState.SyncConnected) {
			countDownLatch.countDown();
		}
		System.out.println();
		System.out.println("--------------->");
		System.out.println("事件路径 : " + event.getPath());
		System.out.println("事件类型 : " + event.getType());
		System.out.println("事件状态 : " + event.getState());
		System.out.println("<---------------");
	}

	/**
	 * 连接资源
	 * 
	 * @param hosts
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void connect(String hosts) throws IOException, InterruptedException {
		zk = new ZooKeeper(hosts, SESSION_TIMEOUT, this);
		countDownLatch.await();
	}

	public void close() throws InterruptedException {
		if (null != zk) {
			try {
				zk.close();
			} catch (InterruptedException e) {
				throw e;
			} finally {
				zk = null;
				System.gc();
			}
		}
	}

}
