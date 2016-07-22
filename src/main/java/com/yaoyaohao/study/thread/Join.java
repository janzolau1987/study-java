package com.yaoyaohao.study.thread;

import java.util.concurrent.TimeUnit;

/**
 * Thread.join()的使用
 * 
 * @author liujianzhu
 * @date 2016年7月22日 下午2:45:31
 */
public class Join {
	public static void main(String[] args) throws Exception{
		Thread previous = Thread.currentThread();
		for(int i=0;i<10;i++) {
			Thread thread = new Thread(new Domino(previous),String.valueOf(i));
			thread.start();
			previous = thread;
		}
		TimeUnit.SECONDS.sleep(5);
		System.out.println(Thread.currentThread().getName() + " terminate.");
	}
	
	static class Domino implements Runnable {
		private Thread thread;
		
		public Domino(Thread thread) {
			this.thread = thread;
		}
		
		@Override
		public void run() {
			try{
				thread.join();
			}catch(InterruptedException e){}
			System.out.println(Thread.currentThread().getName() + " terminate.");
		}
	}
}
