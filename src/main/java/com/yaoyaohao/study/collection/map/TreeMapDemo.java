package com.yaoyaohao.study.collection.map;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * A Red-Black tree based NavigableMap implementation. The map is sorted according
 * to the natual ordering of its keys, or by a Comparator provided at map creation
 * time, depending on which constructor is used.
 * This implementation provides guaranteed log(n) time cost for the containsKey, get,
 * put and remove operations.
 * Algorithms are adaptations of those in Cormen, Leiserson, and Rivest's Introduction to Algorithms.
 * 
 * 与HashMap,LinkedHashMap比较:
 * HashMap不保证数据有序，LinkedHashMap保证数据可以保持插入顺序，而如果希望Map可以保持key的大小顺序时可使用TreeMap
 * 
 * TreeMap利用红黑树来保证key的有序性，同时使用得增删改查时间复杂度为O(log(n))
 * 
 * @author liujianzhu
 * @date 2017年7月7日 下午3:52:39
 */
public class TreeMapDemo {
	public static void main(String[] args) {
		Map<Integer, String> tmap = new TreeMap<>();
		tmap.put(1, "语文");
		tmap.put(3, "英语");
		tmap.put(2, "数学");
		tmap.put(4, "政治");
		tmap.put(5, "历史");
		tmap.put(6, "地理");
		tmap.put(7, "生物");
		tmap.put(8, "化学");
		for(Entry<Integer, String> entry : tmap.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
