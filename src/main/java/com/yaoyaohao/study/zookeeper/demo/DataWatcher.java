package com.yaoyaohao.study.zookeeper.demo;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

/**
 * ZooKeeper -- Watch
 * 
 * @author liujianzhu
 * @date 2016年10月13日 下午5:08:42
 */
public class DataWatcher implements Watcher, Runnable {
	private static String hostPort = "172.16.10.1:2181";
	private static String zooDataPath = "/MyConfig";
	byte zoo_data[] = null;
	ZooKeeper zk;

	public DataWatcher() {
		try {
			zk = new ZooKeeper(hostPort, 2000, this);
			if (zk != null) {
				try {
					if (zk.exists(zooDataPath, this) == null) {
						zk.create(zooDataPath, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					}
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printData() throws KeeperException, InterruptedException {
		zoo_data = zk.getData(zooDataPath, this, null);
		String zString = new String(zoo_data);
		System.out.printf("\nCurrent Data @ ZK Path %s: %s", zooDataPath, zString);
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.printf("\nEvent Received: %s", event.toString());
		// We will process only events of type NodeDataChanged
		if (event.getType() == EventType.NodeDataChanged) {
			try {
				printData();
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws KeeperException, InterruptedException {
		DataWatcher dataWatcher = new DataWatcher();
		dataWatcher.printData();
		dataWatcher.run();
	}

	@Override
	public void run() {
		try {
			synchronized (this) {
				while (true) {
					wait();
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
}
