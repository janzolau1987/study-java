package com.yaoyaohao.study.collection.set;

import java.util.HashSet;
import java.util.Set;

/**
 * This class implements the Set interface, backed by a hash table (actually a HashMap instance).
 * It makes no guarantees as to the iteration order of the set; in particular, it does not guarantee
 * that the order will remain constant over time. This class permits the null element.
 * 
 * HashSet是基于HashMap来实现的，操作很简单，更像是对HashMap做一层“封装”，而且只使用了HashMap的key来实现各种特性
 * 
 * @author liujianzhu
 * @date 2017年7月11日 下午9:13:56
 */
public class HashSetDemo {
	public static void main(String[] args) {
		Set<String> set = new HashSet<>();
		set.add("语文");
		set.add("数学");
		set.add("英语");
		set.add("历史");
		set.add("政治");
		set.add("地理");
		set.add("生物");
		set.add("化学");
		set.add("生物");
		set.add("化学");
		
		for(String v : set) {
			System.out.println(v);
		}
	}
}
