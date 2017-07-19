package com.yaoyaohao.study.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Collections工具类
 * 
 * @author liujianzhu
 * @date 2017年7月19日 下午7:33:42
 */
public class CollectionsDemo {
	public static void main(String[] args) {
		//1.空集合，只读且为空
		System.out.println(Collections.EMPTY_LIST);
		System.out.println(Collections.EMPTY_MAP);
		System.out.println(Collections.EMPTY_SET);
		
		//2.单元素集合:指的是集合只有一个元素且集合只读
		Collections.singletonList(new Object());
		Collections.singleton(new Object());
		Collections.singletonMap("A", "a");
		
		//3.只读集合:一旦初始化后就不能修改，任何修改集合操作都会抛出UnsupportedOperationException异常
		Collections.unmodifiableCollection(new ArrayList<>());
		Collections.unmodifiableList(new ArrayList<>());
		Collections.unmodifiableMap(new HashMap<>());
		Collections.unmodifiableSortedMap(new TreeMap<>());
		Collections.unmodifiableSet(new HashSet<>());
		Collections.unmodifiableSortedSet(new TreeSet<>());
		
		//4.Checked集合:具有检查插入元素类型的特性，如checkedList中元素类为String时，如果插入其它类型则抛出ClassCastException异常
		Collections.checkedCollection(new ArrayList<String>(), String.class);
		Collections.checkedList(new ArrayList<Integer>(), Integer.class);
		Collections.checkedMap(new HashMap<String, Integer>(), String.class, Integer.class);
		Collections.checkedSortedMap(new TreeMap<String, Integer>(), String.class, Integer.class);
		Collections.checkedSet(new HashSet<String>(), String.class);
		Collections.checkedSortedSet(new TreeSet<String>(), String.class);
		
		//5.同步集合：这些集合内部实现都是通过一个mutex(互斥体)来实现对集合操作的同步化
		Collections.synchronizedCollection(new ArrayList<>());
		Collections.synchronizedList(new ArrayList<>());
		Collections.synchronizedMap(new HashMap<>());
		Collections.synchronizedSet(new HashSet<>());
		Collections.synchronizedSortedMap(new TreeMap<>());
		Collections.synchronizedSortedSet(new TreeSet<>());
		
		//6.替换查找
		//6.1 fill - 使用指定元素替换指定列表中所有元素
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("c");
		list.add("d");
		Collections.fill(list, "E");
		System.out.println(list);
		
		//6.2 frequency - 返回指定collection中等于指定 对象的元素数
		System.out.println(Collections.frequency(list, "E"));
		
		//6.3 indexOfSubList - 返回指定源列表中第一次出现指定目标列表的起始位置，如果没有出现这样的列表则返回-1
		List<String> source = Arrays.asList(new String[]{"a","b","c","d","f","g","b","c","d"});
		List<String> target = Arrays.asList(new String[]{"b","c","d"});
		System.out.println(Collections.indexOfSubList(source, target));
		
		//6.4 lastIndexOfSubList - 返回指定源列表中最后一次出现指定目标列表的起始位置，如果没有出现这样的列表则返回-1
		System.out.println(Collections.lastIndexOfSubList(source, target));
		
		//6.5 max - 返回给定collection的最大元素
		System.out.println(Collections.max(source));
		
		//6.6 min - 返回给定collection的最小元素
		System.out.println(Collections.min(source));
		
		//6.7 replaceAll - 使用另一个值替换列表中出现的所有某一指定值
		Collections.replaceAll(source, "b", "B");
		System.out.println(source);
		
		//6.8 binarySearch - 二分查找
		System.out.println(Collections.binarySearch(source, "f")); 
		
		//7 排序
		//7.1 reverse - 对List中的元素倒序排列
		Collections.reverse(source);
		System.out.println(source);
		
		//7.2 shuffle - 对List中的元素随机排列
		Collections.shuffle(source);
		System.out.println(source);
		
		//7.3 sort - 对List中的元素排序
		Collections.sort(source);
		System.out.println(source);
		
		//7.4 swap - 交换List中某两个指定下标位元素在集合中的位置
		Collections.swap(source, 0, source.size() - 1);
		System.out.println(source);
		
		//7.5 rotate - 循环移动
		List<String> slist = Arrays.asList(new String[]{"t","a","b","k","s"});
		Collections.rotate(slist, 1);
		System.out.println(slist);
	}
}
