package com.yaoyaohao.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier同步屏障示例
 * 	
 * 	CyclicBarrier与CountDownLatch区别：
 * 	1、CountDownLatch的计数器只能使用一次，而CyclicBarrier的计数器可以使用reset()方法重置
 * 	2、CyclicBarrier还提供其他有用的方法，如getNumberWaiting方法可以获得阻塞的线程数量。isBroken()方法用来了解阻塞的线程是否被中断
 * 
 * @author liujianzhu
 * @date 2016年8月3日 下午3:03:48
 */
public class CyclicBarrierTest {
	static CyclicBarrier c = new CyclicBarrier(2, new A());
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					c.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
				System.out.println("1");
			}
		}).start();
		
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		System.out.println("2");
	}
	
	static class A implements Runnable {
		@Override
		public void run() {
			System.out.println("3");
		}
	}
}
