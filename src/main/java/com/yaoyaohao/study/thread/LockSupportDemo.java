package com.yaoyaohao.study.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport
 * 
 * @author liujianzhu
 * @date 2016年8月2日 下午2:43:26
 */
public class LockSupportDemo {
	public static void main(String[] args) {
		Thread current = Thread.currentThread();
		/**
		 * Makes available the permit for the given thread, if it was not already available. If the thread was 
		 * blocked on park then it will unblock. Otherwise, its next call to park is guaranteed not to block. 
		 * This operation is not guaranteed to have any effect at all if the given thread has not been started.
		 */
		LockSupport.unpark(current);
		LockSupport.park();
		System.out.println("blocked?");
	}
}
