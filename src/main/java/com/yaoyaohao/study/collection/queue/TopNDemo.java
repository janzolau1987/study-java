package com.yaoyaohao.study.collection.queue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 通过PriorityQueue构建大小堆来解决TopN问题
 * 
 * @author liujianzhu
 * @date 2017年7月11日 下午7:48:57
 */
public class TopNDemo {
	public static void main(String[] args) {
		FixedSizedPriorityQueue<Integer> pq = new FixedSizedPriorityQueue<>(10);
		Random random = new Random();
		int rNum = 0;
		for(int i = 0; i <= 100; i++) {
			rNum = random.nextInt(1000);
			pq.add(rNum);
		}
		
		//PriorityQueue本身的遍历是无序
		Iterator<Integer> iter = pq.iterator();
		while(iter.hasNext()) {
			System.out.print(iter.next() + ",");
		}
		System.out.println();
		//
		while(!pq.isEmpty()) {
			System.out.print(pq.poll() + ",");
		}
	}
}

class FixedSizedPriorityQueue<E extends Comparable<E>> {
	private PriorityQueue<E> queue;
	private int maxSize;
	
	public FixedSizedPriorityQueue(int maxSize) {
		if(maxSize <= 0)
			throw new IllegalArgumentException();
		this.maxSize = maxSize;
		this.queue = new PriorityQueue<>(maxSize, new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				return o2.compareTo(o1);
			}
		});
	}
	
	public void add(E e) {
		if(queue.size() < maxSize) { //未达到最大容量，直接添加 
			queue.add(e);
		} else { //队列已满
			E peek = queue.peek();
			if(e.compareTo(peek) < 0) {
				queue.poll();
				queue.add(e);
			}
		}
	}
	
	public E peek() {
		return queue.peek();
	}
	
	public E poll() {
		return queue.poll();
	}
	
	public boolean isEmpty() {
		return queue.isEmpty();
	}
	
	public Iterator<E> iterator() {
		return queue.iterator();
	}
}
