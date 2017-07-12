package com.yaoyaohao.study.collection.set;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Hash table and linked list implementation of the Set interface, with predictable iteration order.
 * This implementation differs from HashSet in that it maintains a doubly-linked list running through
 * all of its entries. This linked list defines the iteration ordering, which is the order in which
 * elements were inserted inot the set (insertion-order). Note that insertion order is not affected if
 * an element is re-inserted into the set.(An element e is reinserted into a set s if s.add(e) is invoked
 * when s.contain(e) would return true immediately prior to the invocation.)
 * 
 * LinkedHashSet就基于HashMap和双向链表的实现
 * 
 * @author liujianzhu
 * @date 2017年7月12日 上午10:42:04
 */
public class LinkedHashSetDemo {
	public static void main(String[] args) {
		Set<String> lset = new LinkedHashSet<>();
		lset.add("语文");
		lset.add("数学");
		lset.add("英语");
		lset.add("历史");
		lset.add("政治");
		lset.add("地理");
		lset.add("生物");
		lset.add("化学");
		lset.add("生物");
		lset.add("化学");
		for(String str : lset) {
			System.out.println(str);
		}
	}
}
