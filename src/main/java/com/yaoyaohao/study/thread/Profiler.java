package com.yaoyaohao.study.thread;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal的使用
 * 
 * @author liujianzhu
 * @date 2016年7月22日 下午3:24:14
 */
public class Profiler {
	private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
		protected Long initialValue() {
			return System.currentTimeMillis();
		}
	};
	
	public static final void begin() {
		TIME_THREADLOCAL.set(System.currentTimeMillis());
	}
	
	public static final long end() {
		return System.currentTimeMillis() - TIME_THREADLOCAL.get();
	}

	public static void main(String[] args) throws Exception {
		Profiler.begin();
		TimeUnit.SECONDS.sleep(5);
		System.out.println("Cost " + Profiler.end() + " mills");
	}
}
