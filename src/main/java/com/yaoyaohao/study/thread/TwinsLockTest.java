package com.yaoyaohao.study.thread;

import java.util.concurrent.locks.Lock;

/**
 * TwinsLockTest
 * 
 * @author liujianzhu
 * @date 2016年8月1日 下午5:43:50
 */
public class TwinsLockTest {
	public static void main(String[] args) {
		final Lock lock = new TwinsLock();
		class Worker extends Thread {
			@Override
			public void run() {
				//while (true) {
					lock.lock();
					try {
						SleepUtils.second(1);
						System.out.println(Thread.currentThread().getName());
						SleepUtils.second(1);
					} finally {
						lock.unlock();
					}
				//}
			}
		}

		for (int i = 0; i < 10; i++) {
			Worker w = new Worker();
			w.setName("Thread-" + i);
			w.setDaemon(true);
			w.start();
		}

		//
		for (int i = 0; i < 10; i++) {
			SleepUtils.second(1);
			System.out.println();
		}
	}
}
