package com.yaoyaohao.study.zookeeper.queue;

import com.yaoyaohao.study.zookeeper.usecase.queue.DistributedQueue;

public class QueueTest {
	private final static String BARRIER_HOST = "172.16.10.1:2181";

	public static void main(String[] args) {
		DistributedQueue<String> queue = new DistributedQueue<>(BARRIER_HOST, "tq", 10);
		// 添加元素
		System.out.println("添加元素 : " + queue.offer("第一个元素"));
		//弹出元素
		System.out.println("poll获取元素 : " + queue.poll());
		
		//添加元素
		System.out.println("添加元素 : " + queue.offer("第二个元素"));
		//弹出元素
		System.out.println("peek获取元素 : " + queue.peek());
		System.out.println("peek获取元素 : " + queue.peek());
		
		//清空队列
		queue.clear();
		//弹出元素
		System.out.println("poll获取元素 : " + queue.poll());
		System.out.println("peek获取元素 : " + queue.peek());
	}
}
