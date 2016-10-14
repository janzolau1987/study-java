package com.yaoyaohao.study.zookeeper.usecase.barrier;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper典型应用场景 之 分布式Barrier
 * 
 * 用法：
 * DistributeBarrier barrier = new DistributedBarrier(hostPort,barrierName,3);
 * barrier.await();
 * 
 * @author liujianzhu
 * @date 2016年10月14日 上午9:38:47
 */
public class DistributedBarrier implements Watcher {
	private Logger logger = Logger.getLogger(getClass());

	private static final int SESSION_TIMEOUT = 10 * 1000;
	private int timeout; // 会话超时时间
	private String addr; // zk服务地址，host:port格式，多个地址以;隔开
	private String root = "/zk_barriers"; // zk中barrier根目录
	private String barrierName;
	private int barrierSize; // 阈值
	private Integer mutex;

	private CountDownLatch latch = new CountDownLatch(1);

	private ZooKeeper zk = null;

	public DistributedBarrier(String addr, String barrierName, int barrierSize) {
		this(addr, barrierName, barrierSize, SESSION_TIMEOUT);
	}

	public DistributedBarrier(String addr, String barrierName, int barrierSize, int timeout) {
		this.addr = addr;
		this.barrierName = barrierName;
		this.barrierSize = barrierSize;
		this.timeout = timeout;
		init();
	}

	private void init() {
		try {
			this.zk = new ZooKeeper(addr, timeout, this);
			latch.await();
			this.mutex = new Integer(-1);
			// 检查根目录是否存在，若不存在则创建
			initRoot(root);
			this.root = this.root + "/" + this.barrierName;
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

	/**
	 * 如果是连接状态成功，则继续原初始操作； 如果是触发事件后，则唤醒在mutex上的等待线程
	 */
	@Override
	public void process(WatchedEvent event) {
		// 如果zk连接成功
		if (event.getType() == EventType.None && event.getState() == KeeperState.SyncConnected) {
			latch.countDown();
			//return;
		}
		else if(event.getType() == EventType.NodeChildrenChanged) {
			synchronized (mutex) {
				mutex.notifyAll();
			}
		}
	}

	/**
	 * 当新建znode时，首先持有mutex监视器才能进入同步代码块。 当znode发生事件后，会触发process，从而唤醒在mutex上等待的线程。
	 * 通过while循环判断创建的节点个数，当节点个数大于设定的值时，这个await方法才执行完成。
	 * 
	 * @throws Exception
	 */
	public void await() throws Exception {
		zk.create(root + "/c", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		while (true) {
			synchronized (mutex) {
				List<String> list = zk.getChildren(root, true);
				if (list.size() < this.barrierSize) {
					mutex.wait();
				} else {
					//退出等待
					return;
				}
			}
		}
	}
}
