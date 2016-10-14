package com.yaoyaohao.study.zookeeper.barrier;

import com.yaoyaohao.study.zookeeper.usecase.barrier.DistributedBarrier;

/**
 * 分布式barrier测试用例
 * 
 * @author liujianzhu
 * @date 2016年10月14日 上午10:17:55
 */
public class BarrierTest {
	private final static String BARRIER_HOST = "172.16.10.1:2181";

	public static void main(String[] args) {
		final DistributedBarrier barrier = new DistributedBarrier(BARRIER_HOST, "test", 3);
		//
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					barrier.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " is Running....");
			}
		};
		new Thread(r,"Thread-01").start();
		new Thread(r,"Thread-02").start();
		//new Thread(r,"Thread-03").start();
	}
}
