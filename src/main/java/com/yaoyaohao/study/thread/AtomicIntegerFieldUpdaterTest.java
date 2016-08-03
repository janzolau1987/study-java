package com.yaoyaohao.study.thread;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 原子更新字段类
 * 	AtomicIntegerFieldUpdater	原子更新整型的字段的更新器
 * 	AtomicLongFieldUpdater		原子更新长整型的字段的更新器
 * 	AtomicStampedReference		原子更新带有版本号的引用类型 （可以解决CAS更新时出现的ABA问题）	
 * 
 * 原子更新字段类使用说明：
 * 第一步、因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性
 * 第二步、更新类型的字段（属性）必须使用public volatile修饰符
 * 
 * @author liujianzhu
 * @date 2016年8月3日 上午11:04:40
 */
public class AtomicIntegerFieldUpdaterTest {
	//创建原子更新器，并设置需要更新的对象类和对象的属性
	private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater.newUpdater(User.class, "age");
	
	public static void main(String[] args) {
		User conan = new User("Conan",10);
		System.out.println(a.getAndIncrement(conan));
		System.out.println(a.get(conan));
	}
	
	public static class User {
		private String name;
		public volatile int age;
		
		public User(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}
		
		public String getName() {
			return name;
		}
		
		public int getAge() {
			return age;
		}
	}
}
