package com.yaoyaohao.study.zookeeper.lock;

import java.io.IOException;
import java.util.Collections;
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
 * ZooKeeper典型应用场景 之 分布式锁
 * 
 * @author liujianzhu
 * @date 2016年10月14日 下午5:17:56
 */
public class SimpleDistributeLock implements DistributedLock, Watcher {
	private Logger logger = Logger.getLogger(getClass());

	private static final int SESSION_TIMEOUT = 10 * 1000;
	private int timeout; // 会话超时时间
	private String addr; // zk服务地址，host:port格式，多个地址以;隔开
	private String root = "/_locknode_";
	private String lockName;

	private String zname = "lock-";
	
	private Integer mutex;
	private CountDownLatch latch = new CountDownLatch(1);
	private ZooKeeper zk = null;
	
	private volatile int currentLock;

	public SimpleDistributeLock(String addr, String lockName) {
		this(addr, lockName, SESSION_TIMEOUT);
	}

	public SimpleDistributeLock(String addr, String lockName, int timeout) {
		this.addr = addr;
		this.lockName = lockName;
		this.timeout = timeout;
		init();
	}

	private void init() {
		try {
			this.zk = new ZooKeeper(addr, timeout, this);
			latch.await();
			mutex = new Integer(-1);
			// 检查根目录是否存在，若不存在则创建
			initRoot(root);
			this.root = this.root + "/" + this.lockName;
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
		else if(event.getType() == EventType.NodeChildrenChanged) {
			synchronized (mutex) {
				mutex.notifyAll();
			}
		}
	}

	@Override
	public void acquire() throws Exception {
		// 创建锁节点
		String curLockName = zk.create(root + "/" + this.zname, new byte[0], Ids.OPEN_ACL_UNSAFE,
				CreateMode.EPHEMERAL_SEQUENTIAL);
		while(true) {
			synchronized (mutex) {
				List<String> children = zk.getChildren(root, true);
				Collections.sort(children);
				String firstChild = children.get(0);
				//
				Integer acquireLock = Integer.valueOf(curLockName.substring(curLockName.lastIndexOf("-") + 1));
				this.currentLock = Integer.valueOf(firstChild.substring(firstChild.lastIndexOf("-") + 1));
				if(this.currentLock >= acquireLock) {
					//如果当前创建的锁的序号是最小，则认为客户端获取锁
					System.out.println("thread_name=" + Thread.currentThread().getName() + "|attend lock|lock_num=" + currentLock);
					return;
				}
				else {
                    //没有获得锁则等待下次事件的发生
                    System.out.println("thread_name=" + Thread.currentThread().getName() + "|wait lock|lock_num=" + currentLock);
                    mutex.wait();
				}
			}
		}
	}

	@Override
	public void release() throws Exception {
		String lname = String.format("%010d", currentLock);
		String lpath = root + "/" + zname + lname;
        Stat stat = zk.exists(lpath, false);
        if(stat != null) {
        	zk.delete(lpath, -1);
        }
        System.out.println("thread_name=" + Thread.currentThread().getName() + "|release lock|lock_num=" + currentLock);
	}

}
