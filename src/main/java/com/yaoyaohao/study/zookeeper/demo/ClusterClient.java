package com.yaoyaohao.study.zookeeper.demo;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

/**
 * Example - a cluster monitor
 * 
 * @author liujianzhu
 * @date 2016年10月13日 下午7:30:24
 */
public class ClusterClient implements Watcher, Runnable {
	private static String membershipRoot = "/Members";
	ZooKeeper zk;

	public ClusterClient(String hostPort, Long pid) {
		String processId = pid.toString();
		try {
			zk = new ZooKeeper(hostPort, 2000, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (zk != null) {
			try {
				zk.create(membershipRoot + "/" + processId, processId.getBytes(), Ids.OPEN_ACL_UNSAFE,
						CreateMode.EPHEMERAL);
			} catch (KeeperException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized void close() {
		try {
			zk.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
		} finally {
			this.close();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.printf("\nEvent Received : %s", event.toString());
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage:ClusterMonitor <Host:Port>");
			// System.exit(0);
		}
		String hostPort = "172.16.10.1:2181";
		// Get the process id
		String name = ManagementFactory.getRuntimeMXBean().getName();
		int index = name.indexOf("@");
		Long processId = Long.parseLong(name.substring(0, index));
		new ClusterClient(hostPort, processId).run();
	}
}
