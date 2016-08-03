package com.yaoyaohao.study.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子更新基本类型
 *  AtomicInteger 
 *  AtomicBoolean 
 *  AtomicLong
 * 
 * @author liujianzhu
 * @date 2016年8月3日 上午10:44:39
 */
public class AtomicIntergerTest {
	static AtomicInteger ai = new AtomicInteger(1);
	
	public static void main(String[] args) {
		System.out.println(ai.getAndIncrement());
		System.out.println(ai.get());
	}
}
