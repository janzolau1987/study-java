package com.yaoyaohao.study.thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 等待/通知机制
 * 1) 使用wait()、notify()和notifyAll()时需要先对调用对象加锁
 * 2) 调用wait()方法后，线程状态由RUNNING变为WAITING,并将当前线程放置到对象的等待队列
 * 3) notify()或notifyAll()方法调用后，等待线程依旧不会从wait()返回，需要调用notify()
 * 	 或notifyAll()的线程释放锁之后 ，等待线程才有机会从wait()返回
 * 4) notify()方法将等待队列中的一个等待线程从等待队列中移到同步队列中，而notifyAll()方法则是
 * 	将等待队列中所有的线程全部移到同步队列，被移到的线程状态由WAITING变为BLOCKED
 * 5) 从wait()方法返回有前提是获得了调用对象的锁
 * 
 * @author liujianzhu
 *
 */
public class WaitNotify {
	static boolean flag = true;
	static Object lock = new Object();
	
	//
	private static DateFormat df = new SimpleDateFormat("HH:mm:ss");
	
	public static void main(String[] args) throws Exception{
		Thread waitThread = new Thread(new Wait(),"WaitThread");
		waitThread.start();
		TimeUnit.SECONDS.sleep(1);
		Thread notifyThread = new Thread(new Notify(),"NotifyThread");
		notifyThread.start();
	}
	
	static class Wait implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				while(flag) {
					try{
						System.out.println(Thread.currentThread() + " flag is true, wait @ " + df.format(new Date()));
						lock.wait();
					}catch(InterruptedException e){}
				}
				//
				System.out.println(Thread.currentThread() + " flag is false, running @ " + df.format(new Date()));
			}
		}
	}
	
	static class Notify implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				System.out.println(Thread.currentThread() + " hold lock, notify @ " + df.format(new Date()));
				lock.notifyAll();
				flag = false;
				SleepUtils.second(5);
			}
			
			//
			synchronized (lock) {
				System.out.println(Thread.currentThread() + " hold lock again, sleep @ " + df.format(new Date()));
				SleepUtils.second(5);
			}
		}
	}
}
