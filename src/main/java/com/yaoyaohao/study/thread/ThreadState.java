package com.yaoyaohao.study.thread;

/**
 * 运行查看线程状态
 * 	NEW
 * 	RUNNABLE
 * 	BLOCKED
 * 	WAITING
 * 	TIME_WAITING
 * 	TERMINATED
 * 
 * 使用jstack工具查看示例代码运行时的线程信息
 * >jps     --列出当前用户的所有java进程
 * ...
 * >jstack  --用于打印出给定的java进程ID或core file或远程调试服务的Java堆栈信息
 * ...
 * 
 * @author liujianzhu
 * @date 2016年7月18日 下午9:19:28
 *
 */
public class ThreadState {
	public static void main(String[] args) {
		new Thread(new TimeWaiting(),"TimeWaitingThread").start();
		new Thread(new Waiting(),"WaitingThread").start();
		//使用两个Blocked线程，一个获取锁成功，另一个被阻塞
		new Thread(new Blocked(),"BlockedThread-1").start();
		new Thread(new Blocked(),"BlockedThread-2").start();
	}
	
	//该线程不断进行睡眠
	static class TimeWaiting implements Runnable {
		@Override
		public void run() {
			while(true) {
				SleepUtils.second(100);
			}
		}
	}
	
	//该线程在Waiting.class实例上等待
	static class Waiting implements Runnable {
		@Override
		public void run() {
			while(true) {
				synchronized (Waiting.class) {
					try {
						Waiting.class.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//该线程在Blocked.class实例上加锁后，不会释放该锁
	static class Blocked implements Runnable {
		@Override
		public void run() {
			synchronized (Blocked.class) {
				while(true) {
					SleepUtils.second(100);
				}
			}
		}
	}
}
