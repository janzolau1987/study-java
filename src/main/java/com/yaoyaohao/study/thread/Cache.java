package com.yaoyaohao.study.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 基于ReentrantReadWriteLock读写锁实现缓存功能
 * 
 * @author liujianzhu
 * @date 2016年8月2日 上午9:55:23
 */
public class Cache {
	static Map<String, Object> map = new HashMap<>();
	static ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
	static Lock r = rw.readLock();
	static Lock w = rw.writeLock();

	// 获取一个key对应的value
	public static final Object get(String key) {
		r.lock();
		try {
			return map.get(key);
		} finally {
			r.unlock();
		}
	}

	// 设置key对应的value，并返回旧的value
	public static final Object put(String key, Object value) {
		w.lock();
		try {
			return map.put(key, value);
		} finally {
			w.unlock();
		}
	}

	// 清空所有内容
	public static final void clear() {
		w.lock();
		try {
			map.clear();
		} finally {
			w.unlock();
		}
	}
}
