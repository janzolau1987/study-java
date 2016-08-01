package com.yaoyaohao.study.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 自定义同步组件 TwinsLock
 *  - 同一时刻，只允许至多两个线程同时访问，超过两个线程的访问将会被阻塞
 * 
 * @author liujianzhu
 * @date 2016年8月1日 下午5:41:50
 */
public class TwinsLock implements Lock, Serializable {
	private static final long serialVersionUID = 6893722153960193089L;

	private static final class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = 3859762653093738621L;
		
		Sync(int count) {
			if(count <= 0)
				throw new IllegalArgumentException("count must large than zero.");
			setState(count);
		}
		
		@Override
		protected int tryAcquireShared(int arg) {
			for(;;) {
				int current = getState();
				int newCount = current - arg;
				if(newCount < 0 || compareAndSetState(current, newCount))
					return newCount;
			}
		}
		
		@Override
		protected boolean tryReleaseShared(int arg) {
			for(;;) {
				int current = getState();
				int newCount = current + arg;
				if(compareAndSetState(current, newCount))
					return true;
			}
		}
		
		Condition newCondition() {
			return new ConditionObject();
		}
		
		private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
			s.defaultReadObject();
			setState(2);// reset to unlocked state
		}
	}
	
	//
	private final Sync sync = new Sync(2);

	@Override
	public void lock() {
		sync.acquireShared(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquireShared(1) > 0;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.releaseShared(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
	
}
