package com.yaoyaohao.study.collection;

import java.util.Arrays;
import java.util.List;

/**
 * Arrays工具类:
 * 	Arrays位于java.util包下，是一个工具类，主要提供对数组的一些操作
 * 
 * @author liujianzhu
 * @date 2017年7月19日 下午9:08:11
 */
public class ArraysDemo {
	public static void main(String[] args) {
		//1. fill - 给数组赋值
		String[] s = new String[]{"a","b","c","d","e","f","g"};
		Arrays.fill(s, "T");
		printArray(s);
		
		//2. sort - 排序
		s = new String[]{"x","a","b","c","d","e","f","g"};
		Arrays.sort(s);
		printArray(s);
		
		//3. copyOf - 复制数组：其实可理解为集合扩容
		String[] newS = Arrays.copyOf(s, 15);
		printArray(newS);
		
		//4.equals - 比较数组：通过equals方法比较数组中元素是否相等
		System.out.println(Arrays.equals(s, newS));
		
		//5. binarySearch - 查找数组元素:binarySearch最主要方法是binarySearch0，所有的入口方法最后都会调用这个方法。
		System.out.println(Arrays.binarySearch(s, "d"));
		
		//6. asList - 数组集合的转换
		List<String> list = Arrays.asList(s);
		System.out.println(list);
		
	}
	
	private static <T> void printArray(T[] t) {
		StringBuilder sb = new StringBuilder("{");
		for(int i = 0, len = t.length; i < len; i++) {
			if(i == 0)
				sb.append(t[i]);
			else
				sb.append("," + t[i]);
		}
		sb.append("}");
		System.out.println(sb.toString());
	}
}
