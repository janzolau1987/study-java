package com.yaoyaohao.study.thread;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch示例:
 * 1、计数器必须大于等于0，只是等于0时候，计数器就是零，调用await方法时不会阻塞当前线程。
 * 2、CountDownLatch不可能重新初始化或者修改CountDownLatch对象内部计数器的值。
 * 3、一个线程调用countDown方法happen-befores，另一个线程调用await方法。
 * 
 * @author liujianzhu
 * @date 2016年8月3日 下午2:54:43
 */
public class CountDownLatchTest {
	static CountDownLatch c = new CountDownLatch(2);
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("1");
				c.countDown();
				System.out.println("2");
				c.countDown();
			}
		}).start();
		
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("3");
	}
}
