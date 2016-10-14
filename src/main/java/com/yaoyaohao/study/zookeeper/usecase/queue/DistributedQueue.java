package com.yaoyaohao.study.zookeeper.usecase.queue;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import com.yaoyaohao.study.zookeeper.usecase.session.SerializationUtils;

/**
 * ZooKeeper典型应用场景 之 分布式队列
 * 
 * @author liujianzhu
 * @date 2016年10月14日 下午3:08:01
 */
public class DistributedQueue<T> implements Watcher {
	private Logger logger = Logger.getLogger(getClass());

	private static final int SESSION_TIMEOUT = 10 * 1000;
	private int timeout; // 会话超时时间
	private String addr; // zk服务地址，host:port格式，多个地址以;隔开
	private String root = "/_QUEUE_";
	private String queueName;
	private int queueSize;

	private String zname = "queue-";

	private CountDownLatch latch = new CountDownLatch(1);

	private ZooKeeper zk = null;

	public DistributedQueue(String addr, String queueName, int queueSize) {
		this(addr, queueName, queueSize, SESSION_TIMEOUT);
	}

	public DistributedQueue(String addr, String queueName, int queueSize, int timeout) {
		this.addr = addr;
		this.queueName = queueName;
		this.queueSize = queueSize;
		this.timeout = timeout;
		init();
	}

	private void init() {
		try {
			this.zk = new ZooKeeper(addr, timeout, this);
			latch.await();
			// 检查根目录是否存在，若不存在则创建
			initRoot(root);
			this.root = this.root + "/" + this.queueName;
			initRoot(root);
		} catch (IOException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.error(e);
		} catch (KeeperException e) {
			logger.error(e);
		}
	}

	private void initRoot(String root) throws KeeperException, InterruptedException {
		Stat stat = zk.exists(root, false);
		if (stat == null) {
			zk.create(root, new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.None && event.getState() == KeeperState.SyncConnected) {
			latch.countDown();
		}
	}

	/**
	 * 添加元素，如果已满则等待
	 * 
	 * @param t
	 * @return
	 */
	public boolean offer(T t) {
		try {
			while (true) {
				List<String> list = zk.getChildren(root, true);
				// 如果队列已满，则返回false
				if (list.size() >= queueSize) {
					return false;
				} else {
					zk.create(root + "/" + zname, SerializationUtils.serialize(t), Ids.OPEN_ACL_UNSAFE,
							CreateMode.PERSISTENT_SEQUENTIAL);
					return true;
				}
			}
		} catch (KeeperException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.error(e);
		}
		return false;
	}

	/**
	 * 返回第一个元素，并删除；如果已空则返回null
	 * 
	 * @return
	 */
	public T poll() {
		return getFirst(true);
	}

	/**
	 * 返回第一个元素
	 * 
	 * @return
	 */
	public T peek() {
		return getFirst(false);
	}

	@SuppressWarnings("unchecked")
	private T getFirst(boolean isRemove) {
		try {
			List<String> children = zk.getChildren(root, true);
			if (!children.isEmpty()) {
				Collections.sort(children);
				String firstPath = children.get(0);
				//
				byte[] data = zk.getData(root + "/" + firstPath, false, null);
				if (isRemove) {
					zk.delete(root + "/" + firstPath, -1);
				}
				T t = (T) SerializationUtils.deserialize(data);
				return t;
			}
		} catch (KeeperException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.error(e);
		}
		return null;
	}

	/**
	 * 清空队列
	 */
	public void clear() {
		try {
			List<String> children = zk.getChildren(root, true);
			for (String child : children) {
				zk.delete(root + "/" + child, -1);
			}
		} catch (KeeperException e) {
			logger.error(e);
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}
