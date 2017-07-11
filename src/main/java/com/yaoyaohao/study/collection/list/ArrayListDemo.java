package com.yaoyaohao.study.collection.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Resizable-array implementation of the List interface. Implements all optional list
 * operations, and permits all elements, including null. In addition to implementing the
 * List interface, this class provides methods to manipulate the size of the array that
 * is used internally to store the list. (This class is roughly equivalent to Vector, except
 * that it is unsynchronized.)
 * 
 * ArrayList的最重要特点就是“自动扩容”，俗称“动态数组”
 * 
 * 底层以数据实现。节约空间，但数组有容量限制。超出限制时会增加50%容量，用System.arrayCopy()复制到新数组。
 * 
 * @author liujianzhu
 * @date 2017年7月10日 下午5:52:23
 */
public class ArrayListDemo {
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("语言:99");
		list.add("数学:98");
		list.add("英语:100");
		list.remove(0);
		//
		System.out.println(list);
		
		String[] str = new String[10];
		for(int i = 1; i <= 10; i++) 
			str[i - 1] = "" + i;
		printArray(str);
		
		System.out.println();
		
		list.toArray(str);
		printArray(str);
	}
	
	private static <T> void printArray(T[] array) {
		for(T t : array)
			System.out.print(t + " , ");
	}

}
