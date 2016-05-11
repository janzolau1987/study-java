package com.yaoyaohao.study.cache;

import java.util.Map.Entry;

import org.junit.Test;

public class CacheTest {

	@Test
	public void test() {
		LRUCache<String, String> cache = new LRUCache<String, String>(4);
		cache.put("1", "one");
		cache.put("2", "two");
		cache.put("3", "three");
		cache.put("4", "four");
		
		System.out.println(cache.get("1"));
		
		cache.put("5", "five");
		//
		for(Entry<String,String> e : cache.getAll()) 
			System.out.println(e.getKey() + " : " + e.getValue());
	}

}
