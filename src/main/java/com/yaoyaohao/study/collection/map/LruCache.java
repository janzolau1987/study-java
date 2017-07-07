package com.yaoyaohao.study.collection.map;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于LinkedHashMap实现LRU Cache
 * 
 * @author liujianzhu
 * @date 2017年7月7日 上午11:54:12
 */
public class LruCache {
	private int capacity; // 最大容量
	private Map<String, Integer> cache;

	public LruCache(final int capacity) {
		this.capacity = capacity;
		this.cache = new LinkedHashMap<String, Integer>(capacity, 0.75f, true) {
			private static final long serialVersionUID = 459201017451983714L;

			@Override
			protected boolean removeEldestEntry(Map.Entry eldest) {
				return this.size() > LruCache.this.capacity;
			}
		};
	}

	public int get(String key) {
		if (cache.containsKey(key))
			return cache.get(key);
		else
			return -1;
	}

	public void put(String key, Integer value) {
		cache.put(key, value);
	}
	
	public static void main(String[] args) {
		LruCache cache = new LruCache(5);
		cache.put("001", 1);
		cache.put("002", 2);
		cache.put("003", 3);
		cache.put("004", 4);
		System.out.println(cache.cache);
		cache.put("005", 5);
		cache.put("006", 6);
		System.out.println(cache.cache);
		//
		System.out.println("get : " + cache.get("001"));
		System.out.println(cache.cache);
		System.out.println("get : " + cache.get("003"));
		System.out.println(cache.cache);
	}
}
