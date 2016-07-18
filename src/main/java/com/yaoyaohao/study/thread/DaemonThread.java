package com.yaoyaohao.study.thread;

/**
 * 当一个Java虚拟机中不存在非daemon线程的时候，java虚拟机将会退出
 * 
 * daemon属性需要在启动线程之前设置，不能在启动线程之后设置
 * 
 * @author liujianzhu
 * @date 2016年7月18日 下午9:35:33
 *
 */
public class DaemonThread {
	public static void main(String[] args) {
		Thread thread = new Thread(new DaemonRunner(),"DaemonRunner");
		thread.setDaemon(true);
		thread.start();
	}
	
	static class DaemonRunner implements Runnable {
		@Override
		public void run() {
			try{
				SleepUtils.second(10);
			}finally {
				System.out.println("DaemonThread finally run.");
			}
		}
	}
}
