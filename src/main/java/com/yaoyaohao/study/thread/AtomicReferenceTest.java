package com.yaoyaohao.study.thread;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子更新引用类型
 * 	AtomicReference				原子更新引用类型
 * 	AtomicReferenceFieldUpdater	原子更新引用类型里的字段
 * 	AtomicMarkableReference		原子更新带有标记位的引用类型
 * 
 * @author liujianzhu
 * @date 2016年8月3日 上午10:53:02
 */
public class AtomicReferenceTest {
	
	public static AtomicReference<User> atomicUserRef = new AtomicReference<>();
	
	public static void main(String[] args) {
		User user = new User("Conan",15);
		atomicUserRef.set(user);
		User updateUser = new User("Zhangsan",17);
		atomicUserRef.compareAndSet(user, updateUser);
		//
		System.out.println(atomicUserRef.get().getName());
		System.out.println(atomicUserRef.get().getAge());
	}
	
	static class User {
		private String name;
		private int age;
		
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
