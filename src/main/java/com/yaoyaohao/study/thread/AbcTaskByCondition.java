package com.yaoyaohao.study.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Condtion实现连续输出ABC任务:
 * 	三个线程，线程一输出10次A，线程二输出10次B，线程三输出10次C，要求三个线程按ABC的顺序输出
 * 
 * @author liujianzhu
 * @date 2016年8月4日 上午10:23:59
 */
public class AbcTaskByCondition {
	private static final Lock lock = new ReentrantLock();

	public static void main(String[] args) throws InterruptedException {
		Condition conditionA = lock.newCondition();
		Condition conditionB = lock.newCondition();
		Condition conditionC = lock.newCondition();
		//
		new Thread(new AbcTask("A", conditionC, conditionA)).start();
		Thread.sleep(10);
		new Thread(new AbcTask("B", conditionA, conditionB)).start();
		Thread.sleep(10);
		new Thread(new AbcTask("C", conditionB, conditionC)).start();
	}

	static class AbcTask implements Runnable {
		private String name;
		private Condition pre;
		private Condition cur;

		public AbcTask(String name, Condition pre, Condition cur) {
			super();
			this.name = name;
			this.pre = pre;
			this.cur = cur;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				lock.lock();
				try {
					if("C".equals(this.name))
						System.out.println(this.name);
					else
						System.out.print(this.name);
					
					cur.signal();
					if (i != 9)
						pre.await();
				} catch (InterruptedException e) {
					/* IGNORE ERROR */
				} finally {
					lock.unlock();
				}
			}
		}
	}
}
