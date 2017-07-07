package com.yaoyaohao.study.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Hash table based implementation of the Map interface. This implementation provides all
 * of the optional map operations, and permits null values and the null key.(The HashMap class
 * is roughly equivalent to Hashtable, except that is it unsynchronized and permits nulls.)
 * This class makes no guarantees as to the order of the map; in particular, it does not
 * guarantee that the order will remain constant over time.
 * 
 * 关键信息：
 *  基于Map接口实现
 *  允许null键/值
 *  非同步
 *  不保证有序，也不保证顺序不随时间变化
 *  
 *  利用哈希表的快速插入、查找实现O(1)的增删改查
 * 
 * @author liujianzhu
 * @date 2017年7月6日 下午3:16:45
 */
public class HashMapDemo {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
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
