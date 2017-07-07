package com.yaoyaohao.study.collection.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Hash table and linked list implementation of the Map interface, with predictable
 * iteration order. This implementation differs from HashMap in that it maintains a 
 * doubly-linked list running through all of its entries. This linked list defines
 * the iteration ordering, which is normally the order in which keys were inserted
 * inot the map (insertion-order).
 * 
 * LinkedHashMap是Hash表和链表的实现，并且依靠双向链表保证了迭代顺序是插入的顺序。
 * 在HashMap中提到下面的定义(JDK1.8)：
 * 
 * \\Callbacks to allow LinkedHashMap post-actions
 * void afterNodeAccess() {}
 * void afterNodeInsertion(boolean evict) {}
 * void afterNodeRemoval(Node<K,V> p) {}
 * 
 * LinkedHashMap继承于HashMap，因此也重新实现了这3个函数，这三个函数的作用就是在节点访问后、节点插入后、节点移除后做一些事情
 * 
 * @author liujianzhu
 * @date 2017年7月6日 下午9:21:46
 */
public class LinkedHashMapDemo {
	public static void main(String[] args) {
		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("语文", 1);
		map.put("数学", 2);
		map.put("英语", 3);
		map.put("历史", 4);
		map.put("政治", 5);
		map.put("地理", 6);
		map.put("生物", 7);
		map.put("化学", 8);
		//
		for(Entry<String, Integer> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
