package com.yaoyaohao.study.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于AQS实现简单的独占锁
 * 
 * @author liujianzhu
 * @date 2016年8月1日 上午11:56:49
 */
public class Mutex implements Lock , Serializable {
	private static final long serialVersionUID = -3091479841019783744L;

	// 静态内部类，自定义同步器
	private static class Sync extends AbstractQueuedSynchronizer {
		private static final long serialVersionUID = -8389776713511862512L;

		// 是否处于占用状态
		@Override
		protected boolean isHeldExclusively() {
			return getState() == 1;
		}

		// 当前状态为0的时候获取锁
		@Override
		protected boolean tryAcquire(int accquires) {
			assert accquires == 1;// Otherwise unused
			if (compareAndSetState(0, 1)) {
				setExclusiveOwnerThread(Thread.currentThread());
				return true;
			}
			return false;
		}

		// 释放锁
		@Override
		protected boolean tryRelease(int releases) {
			assert releases == 1;
			if (getState() == 0)
				throw new IllegalMonitorStateException();
			setExclusiveOwnerThread(null);
			setState(0);
			return true;
		}

		Condition newCondition() {
			return new ConditionObject();
		}

		private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
			s.defaultReadObject();
			setState(0);// reset to unlocked state
		}
	}
	
	//The sync object does all the hard work,We just forward to it
	//仅需要将操作代理到Sync上即可
	private final Sync sync = new Sync();

	@Override
	public void lock() {
		sync.acquire(1);
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		sync.acquireInterruptibly(1);
	}

	@Override
	public boolean tryLock() {
		return sync.tryAcquire(1);
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireNanos(1, unit.toNanos(time));
	}

	@Override
	public void unlock() {
		sync.release(1);
	}

	@Override
	public Condition newCondition() {
		return sync.newCondition();
	}
	
	public boolean isLocked() {
		return sync.isHeldExclusively();
	}
	
	public boolean hasQueuedThreads() {
		return sync.hasQueuedThreads();
	}
}
