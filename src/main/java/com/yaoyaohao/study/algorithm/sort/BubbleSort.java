package com.yaoyaohao.study.algorithm.sort;

/**
 * 冒泡排序:
 * 冒泡排序是一种简单的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。
 * 	走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。
 * 
 * 具体算法描述如下：
 * <1>.比较相邻的元素。如果第一个比第二个大，就交换它们两个；
 * <2>.对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
 * <3>.针对所有的元素重复以上的步骤，除了最后一个；
 * <4>.重复步骤1~3，直到排序完成。
 * 
 * @author liujianzhu
 * @date 2017年7月20日 下午5:23:59
 */
public class BubbleSort {
	/**
	 * 原始版本排序实现
	 * @param arr
	 */
	public static <T extends Object & Comparable<T>> void bubbleSort(T[] arr) {
		long beginTime = System.nanoTime();
		
		int len = arr.length;
		for(int i = 0; i < len; i++) {
			for(int j = 0; j < len - 1 - i; j++) {
				if(arr[j].compareTo(arr[j + 1]) > 0) {
					T temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
		}
		
		System.out.println("传统排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	/**
	 * 改进版本1：设置一标志性变量pos，用于记录每趟排序中最后一次进行交换的位置。由于pos位置之后的
	 * 记录均已交换到位，故在进行下一趟排序时只要扫描到pos位置即可。
	 */
	public static <T extends Object & Comparable<T>> void bubbleSort2(T[] arr) {
		long beginTime = System.nanoTime();
		
		int i = arr.length - 1;
		while(i > 0) {
			int pos = 0;//每趟开始时，无记录交换
			for(int j = 0; j < i; j++) {
				if(arr[j].compareTo(arr[j + 1]) > 0) {
					T temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
				}
			}
			i = pos;//为下一趟排序作准备
		}
		
		System.out.println("改进一排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	//测试
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		//1.原始版本
		BubbleSort.bubbleSort(arr);
		printArray(arr);
		
		//2.改进版本一
		arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		BubbleSort.bubbleSort2(arr);
		printArray(arr);
	}
	
	private static <T> void printArray(T[] t) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0, len = t.length; i < len; i++) {
			if(i == 0)
				sb.append(t[i]);
			else
				sb.append("," + t[i]);
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
}
