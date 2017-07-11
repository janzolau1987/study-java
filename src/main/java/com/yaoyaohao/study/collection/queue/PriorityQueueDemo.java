package com.yaoyaohao.study.collection.queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

/**
 * 场景：队列是遵循先进先出(FIFO)模式的，但有时需要在队列中基于优先级处理对象。如客户请求的优先级队列
 * 
 * PriorityQueue类是在Java1.5中引入作为J.C.F的一部分。PriorityQueue是基于优先堆的一个无界队列，这个优先
 * 队列中的元素可以默认自然排序或者通过提供的Comparator在队列实例化的排序。
 * 
 * 优先队列不允许空值，而且不支持non-comparable对象。要求使用Comparable和Comparator接口给对象排序，并且在排序时会
 * 按照优先级处理其中的元素。
 * 
 * 优先队列的头是基于自然排序或者Comparator排序的最小元素。如果有多个对象拥有同样的排序，则可能随机取其中任意一个。当获取队列时返回队列的头对象。
 * 
 * 优先队列的大小是不受限制的，但在创建时可以指定初始大小。当向优先队列增加元素时队列大小会自动增加。
 * 
 * PriorityQueue是非线程安全的，所以Java提供了PriorityBlockingQueue(实现BlockingQueue接口)用于Java多线程环境。
 * 
 * 
 * @author liujianzhu
 * @date 2017年7月11日 下午4:33:22
 */
public class PriorityQueueDemo {
	public static void main(String[] args) {
		//自然排序 
		Queue<Integer> integerQueue = new PriorityQueue<>();
		Random rand = new Random();
		for(int i = 0; i < 7; i++) {
			integerQueue.add(new Integer(rand.nextInt(100)));
		}
		
		for(int i = 0; i < 7; i++) {
			Integer id = integerQueue.poll();
			System.out.println("Processing Integer : " + id);
		}
		
		//
		Queue<Customer> customerQueue = new PriorityQueue<>(7, idComparator);
		batchAddDataToQueue(customerQueue);
		pollDataFromQueue(customerQueue);
	}
	
	static void batchAddDataToQueue(Queue<Customer> customerQueue) {
		Random rand = new Random();
		for(int i = 0; i < 7; i++) {
			int id = rand.nextInt(100);
			customerQueue.add(new Customer(id, "Test" + id));
		}
	}
	
	static void pollDataFromQueue(Queue<Customer> customerQueue) {
		while(true) {
			Customer cust = customerQueue.poll();
			if(cust == null)
				break;
			System.out.println("Processing Customer with ID = " + cust.getId());
		}
	}
	
	static Comparator<Customer> idComparator = new Comparator<Customer>() {
		public int compare(Customer o1, Customer o2) {
			return o1.getId() - o2.getId();
		}
	};
}

class Customer {
	private int id;
	private String name;
	
	public Customer(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
