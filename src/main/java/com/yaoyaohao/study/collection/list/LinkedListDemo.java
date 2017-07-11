package com.yaoyaohao.study.collection.list;

import java.util.LinkedList;
import java.util.List;

/**
 * 以双向链表实现。链表无容量限制，但双向链表本身使用了更多空间，也需要额外的链表指针操作。
 * 按下标访问元素 -- get(i)/set(i,e)要悲剧的遍历链表将指针移动到位(但i>数据大小的一半，会从末尾移起)。
 * 插入、删除元素时修改前后节点的指针即可，但还是要遍历部分链表的指针才能移动到下标所指定的位置，只有在链表两头的操作
 * -- add(),addFirst(),removeLast()或用iterator()上的remove()能省掉指针的移动。
 * 
 * @author liujianzhu
 * @date 2017年7月11日 上午11:10:22
 */
public class LinkedListDemo {
	public static void main(String[] args) {
		List<String> list = new LinkedList<>();
		list.add("语言:1");
		list.add("数学:2");
		list.add("英语:3");
		
		System.out.println(list);
	}
}
