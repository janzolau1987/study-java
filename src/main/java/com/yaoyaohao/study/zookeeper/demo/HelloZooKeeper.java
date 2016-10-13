package com.yaoyaohao.study.zookeeper.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * First ZooKeeper Program
 * 
 * @author liujianzhu
 * @date 2016年10月13日 下午4:32:51
 */
public class HelloZooKeeper {
	public static void main(String[] args) throws IOException{
		String hostPort = "172.16.10.1:2181";
		String zpath = "/";
		//
		List<String> zooChildren = new ArrayList<>();
		ZooKeeper zk = new ZooKeeper(hostPort, 2000, null);
		if (zk != null) {
			try {
				zooChildren = zk.getChildren(zpath, false);
				System.out.println("Znodes of '/':");
				for (String child : zooChildren) {
					System.out.println(child);
				}
			} catch (KeeperException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
