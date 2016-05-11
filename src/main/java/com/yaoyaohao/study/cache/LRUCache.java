package com.yaoyaohao.study.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 基于LinkedHashMap实现LRU cache
 * 
 * @author liujianzhu
 * @date 2016年5月11日 下午7:06:53
 *
 */
public class LRUCache<K,V> {
	private static final float hashTableLoadFactor = 0.75f;
	
	private LinkedHashMap<K, V> map;
	private int cacheSize;
	
	/**
	 * 创建LRUCache实例
	 * @param cacheSize 缓存中存放的最大数量
	 */
	@SuppressWarnings("serial")
	public LRUCache(int cacheSize) {
		this.cacheSize = cacheSize;
		int hashTableCapacity = (int) Math.ceil(cacheSize / hashTableLoadFactor) + 1;
		this.map = new LinkedHashMap<K, V>(hashTableCapacity, hashTableLoadFactor, true){
			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > LRUCache.this.cacheSize;
			}
		};
	}
	
	/**
	  * 从缓存中查找节点数据，通过LinkedHashMap实现被访问的节点会成为MRU结点
	  * 
	  * @param key	
	  * @return	key对应的value值，无值为空
	 */
	public synchronized V get(K key) {
		return map.get(key);
	}
	
	/**
	  * 增加结点到缓存中，新加入的节点成为MRU结点。
	  * 如果所加节点在缓存中已存在，则覆盖缓存中值；
	  * 如果缓存中记录数已满，则将LRU结点从缓存中移除
	  * @param key
	  * @param value
	 */
	public synchronized void put(K key,V value) {
		map.put(key, value);
	}
	
	/**
	 * 清空缓存
	 */
	public synchronized void clear(){
		map.clear();
	}
	
	/**
	 * 缓存的结点数
	  * 
	  * @return
	 */
	public synchronized int usedSize() {
		return map.size();
	}
	
	/**
	 * 
	  * 
	  * @return
	 */
	public synchronized Collection<Map.Entry<K, V>> getAll() {
		return new ArrayList<Map.Entry<K,V>>(map.entrySet());
	}
}
