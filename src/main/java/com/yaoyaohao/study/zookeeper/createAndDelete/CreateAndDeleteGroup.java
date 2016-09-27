package com.yaoyaohao.study.zookeeper.createAndDelete;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * 创建/删除分组
 * 
 * @author liujianzhu
 * @date 2016年9月27日 下午5:09:42
 */
public class CreateAndDeleteGroup extends ConnectionWatcher {

	/**
	 * 创建分组
	 * @param groupName
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void create(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		String createPath = zk.create(path, "hello".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println("创建成功--> : " + createPath);
	}
	
	/**
	 * 删除分组
	 * @param groupName
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void delete(String groupName) throws KeeperException,InterruptedException {
		String path = "/" + groupName;
		List<String> children = zk.getChildren(path, false);
		for(String child : children) {
			zk.delete(path + "/" + child, -1);
		}
		zk.delete(path, -1); //版本号为-1，表示无论版本号多少都会直接将其删除
		System.out.println("删除成功--->");
	}
	
	/**
	 * 判断节点是否存在
	 * @param groupName
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
	public void exists(String groupName) throws KeeperException, InterruptedException {
		String path = "/" + groupName;
		Stat stat = zk.exists(path, false);
		if(stat == null) {
			System.out.println("节点 " + path + "不存在.");
		}
		else {
			System.out.println("节点 " + path + "信息为 ： " + stat);
		}
	}
}
