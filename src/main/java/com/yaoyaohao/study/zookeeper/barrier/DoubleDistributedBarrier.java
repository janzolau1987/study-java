package com.yaoyaohao.study.zookeeper.barrier;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

/**
 * ZooKeeper典型应用场景 之 双重分布式barrier
 * 用法：
 * DoubleDistributedBarrier barrier = new DoubleDistributedBarrier(hostPort,barrierName,5);
 * barrier.enterAwait();
 * ...
 * ...
 * barrier.leaveAwait();
 *  
 * @author liujianzhu
 * @date 2016年10月14日 下午1:45:36
 */
public class DoubleDistributedBarrier implements Watcher {
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
	
	private ThreadLocal<String> holder = new ThreadLocal<>();

	public DoubleDistributedBarrier(String addr, String barrierName, int barrierSize) {
		this(addr, barrierName, barrierSize, SESSION_TIMEOUT);
	}

	public DoubleDistributedBarrier(String addr, String barrierName, int barrierSize, int timeout) {
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

	@Override
	public void process(WatchedEvent event) {
		if (event.getType() == EventType.None && event.getState() == KeeperState.SyncConnected) {
			latch.countDown();
		} else if (event.getType() == EventType.NodeChildrenChanged) {
			synchronized (mutex) {
				mutex.notifyAll();
			}
		}
	}

	/**
	 * 执行前统一等待，等到线程都到齐再继续执行
	 * 
	 * @throws Exception
	 */
	public void enterAwait() throws Exception {
		String cpath = zk.create(root + "/c", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		holder.set(cpath); //保存到ThreadLocal中
		while (true) {
			synchronized (mutex) {
				List<String> list = zk.getChildren(root, true);
				if (list.size() < this.barrierSize) {
					mutex.wait();
				} else {
					// 退出等待
					return;
				}
			}
		}
	}

	/**
	 * 执行结束后统一等待，等到所有线程都执行完再继续执行
	 * 
	 * @throws Exception
	 */
	public void leaveAwait() throws Exception {
		//删除本线程创建的znoe
		String path = holder.get();
		if(zk.exists(path, false) != null) {
			zk.delete(path, -1);
		}
		while (true) {
			synchronized (mutex) {
				List<String> list = zk.getChildren(root, true);
				if (!list.isEmpty()) {
					mutex.wait();
				} else {
					// 退出等待
					return;
				}
			}
		}
	}

}
