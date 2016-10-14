package com.yaoyaohao.study.zookeeper.usecase.session;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 连接zookeeper服务器的实现类
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午3:11:33
 */
public class ConnectionWatcher implements Watcher {
	private Logger log = Logger.getLogger(getClass());
	
	private static final int SESSION_TIMEOUT = 5000;
	private CountDownLatch signal = new CountDownLatch(1);
	
	public ZooKeeper connection(String servers) {
		ZooKeeper zk;
		try{
			zk = new ZooKeeper(servers,SESSION_TIMEOUT,this);
			signal.await();
			return zk;
		} catch(IOException e) {
			log.error(e);
		} catch(InterruptedException e) {
			log.error(e);
		}
		return null;
	}

	@Override
	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		if(state == KeeperState.SyncConnected) {
			signal.countDown();
		}
	}

}
