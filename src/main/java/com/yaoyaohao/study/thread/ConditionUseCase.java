package com.yaoyaohao.study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition示例
 * 
 * @author liujianzhu
 * @date 2016年8月2日 下午3:09:44
 */
public class ConditionUseCase {
	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();
	
	public void conditionWait() throws InterruptedException {
		lock.lock();
		try{
			condition.await();
		} finally {
			lock.unlock();
		}
	}
	
	public void conditionSignal() {
		lock.lock();
		try{
			condition.signal();
		} finally {
			lock.unlock();
		}
	}
}
