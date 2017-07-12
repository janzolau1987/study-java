package com.yaoyaohao.study.collection.set;

import java.util.Set;
import java.util.TreeSet;

/**
 * A NavigableSet implementation based on a TreeMap. The elements are ordered using their natual
 * ordering, or by a Comparator provided at set creation time, depending on which constructor is used.
 * This implementation provides guaranteed log(n) time cost for the basic operations(add, remove and contains).
 * 
 * TreeSet是基于TreeMap实现的，同样只是用key及其操作，然后把value置为dummy的object.
 * 
 * 利用TreeMap的特性，实现Set的有序性（通过红黑树）
 * 
 * @author liujianzhu
 * @date 2017年7月12日 上午10:51:05
 */
public class TreeSetDemo {
	public static void main(String[] args) {
		Set<String> tset = new TreeSet<String>();
		tset.add("1语文");
		tset.add("3英语");
		tset.add("2数学");
		tset.add("4政治");
		tset.add("5历史");
		tset.add("6地理");
		tset.add("7生物");
		tset.add("8化学");
		for(String str : tset) {
			System.out.println(str);
		}
	}
}
