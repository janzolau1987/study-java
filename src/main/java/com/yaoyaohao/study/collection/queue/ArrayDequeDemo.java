package com.yaoyaohao.study.collection.queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Resizable-array implementation of the Deque interface. Array deques have no capacity
 * restrictions; they grow as necessary to support usage. They are not thread-safe; in the
 * absence of external synchronization, they do not support concurrent access by multiple
 * threads. Null elements are prohibited. This class is likely to be faster than Stack when
 * used as a stack, and faster than LinkedList when used as a queue.
 * 
 * 以循环数组实现的双向Queue。大小是2的倍数，默认是16.
 * 普通数组只能快速在末尾添加元素，为了支持FIFO，从数组头快速取出元素，就需要使用循环数组，有队头队尾两个下标：
 * 		弹出元素时，队头下标递增；加入元素时，如果已到数组空间的末尾，则将元素循环赋值到数组[0]，同时队尾下标指向0，再弹出下一个元素则赋值
 * 	到数组[1]，队尾下标指向1.如果队尾的下标追上队头，说明数组所有空间已用完，进行双倍的数组扩容。
 * 
 * @author liujianzhu
 * @date 2017年7月11日 下午8:40:12
 */
public class ArrayDequeDemo {
	public static void main(String[] args) {
		Deque<String> deque = new ArrayDeque<>();
		deque.offer("语文:90");
		deque.offerLast("数学:80");
		deque.offerFirst("英语:99");
		//
		while(!deque.isEmpty()) {
			System.out.println(deque.poll());
		}
	}
}
