package com.yaoyaohao.study.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier同步屏障示例
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
