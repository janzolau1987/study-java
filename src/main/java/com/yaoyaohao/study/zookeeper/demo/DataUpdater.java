package com.yaoyaohao.study.zookeeper.demo;

import java.io.IOException;
import java.util.UUID;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * update the data field of the znode path /MyConfig
 * 
 * @author liujianzhu
 * @date 2016年10月13日 下午7:06:09
 */
public class DataUpdater implements Watcher {
	private static String hostPort = "172.16.10.1:2181";
	private static String zooDataPath = "/MyConfig";
	ZooKeeper zk;

	public DataUpdater() {
		try {
			zk = new ZooKeeper(hostPort, 2000, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() throws InterruptedException, KeeperException {
		while (true) {
			String uuid = UUID.randomUUID().toString();
			byte zoo_data[] = uuid.getBytes();
			zk.setData(zooDataPath, zoo_data, -1);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.printf("\nEvent Received: %s", event.toString());
	}

	public static void main(String[] args) throws InterruptedException, KeeperException {
		DataUpdater dataUpdater = new DataUpdater();
		dataUpdater.run();
	}
}
