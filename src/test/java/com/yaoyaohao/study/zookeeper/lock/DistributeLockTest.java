package com.yaoyaohao.study.zookeeper.lock;

public class DistributeLockTest {
	private final static String BARRIER_HOST = "172.16.10.1:2181";

	public static void main(String[] args) {
		final DistributedLock lock = new SimpleDistributeLock(BARRIER_HOST, "test");
		/**
		 * 所有的线程都共享一个锁对象，验证锁对象的线程安全性 锁是阻塞的
		 */
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						lock.acquire();
						Thread.sleep(1000); // 获得锁之后可以进行相应的处理
						System.out.println("======获得锁后进行相应的操作======");
						lock.release();
						System.err.println("=============================");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
	}

}
