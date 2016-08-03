package com.yaoyaohao.study.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 示例
 * 用来控制同时访问特定资源的线程数量，它通过协调各个线程，以保证合理的使用公共资源
 * 
 * @author liujianzhu
 * @date 2016年8月3日 下午3:38:47
 */
public class SemaphoreTest {
	private static final int THREAD_COUNT = 30;
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);	
	
	private static Semaphore s = new Semaphore(10);
	
	public static void main(String[] args) {
		for(int i = 0; i < THREAD_COUNT; i++) {
			threadPool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						s.acquire();
						System.out.println("save data : " + System.currentTimeMillis());
						s.release();
					} catch(InterruptedException e){}
				}
			});
		}
		threadPool.shutdown();
	}
}
