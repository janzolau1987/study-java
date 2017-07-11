package com.yaoyaohao.study.collection.list;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 并发优化的ArrayList.用CopyOnWrite策略，在修改时先复制一个快照来修改，改完再让内部指针指向新数组。
 * 因为对快照的修改对读操作来说不可见，所以只有写锁没有读锁，加上复制的昂贵成本 ，典型的适合读多写少的场景。
 * 如果更新频率较高，或数组较大时，还是Collections.synchronizedList(list)，对所有操作用同一把锁保证线程安全更好。
 * 
 * @author liujianzhu
 * @date 2017年7月11日 下午2:22:55
 */
public class CopyOnWriteArrayListDemo {
	public static void main(String[] args) {
		List<String> list = new CopyOnWriteArrayList<>();
		list.add("语文：1");
		list.add("数学：2");
		list.add("英语：3");
		
		//
		System.out.println(list);
		
		list.remove(2);
		System.out.println(list);
	}
}
